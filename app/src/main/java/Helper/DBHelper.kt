package Helper

import DBObject.*
import Domain.*
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.*

class DBHelper(var context: Context, private var DB_VERSION: Int = CURRENT_VERSION) :
    ManagedSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME = "database.db"
        val CURRENT_VERSION = 1
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context, version: Int = 0): DBHelper {
            if (instance == null) {
                instance = if (version > 0) DBHelper(ctx.applicationContext, version)
                else DBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            speciality.NAME,true,
            speciality.id to INTEGER + PRIMARY_KEY,
            speciality.name1 to TEXT,
            speciality.depart to TEXT
        )
        db.createTable(
            semester.NAME,true,
            semester.id to INTEGER + PRIMARY_KEY,
            semester.name1 to TEXT
        )
        db.createTable(
            admin.NAME, true,
            admin.id to INTEGER + PRIMARY_KEY,
            admin.name1 to TEXT,
            admin.username to TEXT,
            admin.password to TEXT
        )
        db.createTable(
            student.NAME, true,
            student.id to INTEGER + PRIMARY_KEY,
            student.name1 to TEXT,
            student.username to TEXT,
            student.password to TEXT,
            student.sex to TEXT,
            student.depart to TEXT,
            student.speciality to TEXT,
            student.class1 to TEXT
        )
        db.createTable(
            teacher.NAME, true,
            teacher.id to INTEGER + PRIMARY_KEY,
            teacher.name1 to TEXT,
            teacher.username to TEXT,
            teacher.password to TEXT,
            teacher.class1 to TEXT,
            teacher.depart to TEXT,
            teacher.course to TEXT
        )
        db.createTable(
            classlist.NAME, true,
            classlist.id to INTEGER + PRIMARY_KEY,
            classlist.name1 to TEXT,
            classlist.speciality to TEXT
        )
        db.createTable(
            depart.NAME, true,
            depart.id to INTEGER + PRIMARY_KEY,
            depart.name1 to TEXT,
            depart.type to INTEGER
        )
        db.createTable(
            performance.NAME, true,
            performance.course to TEXT,
            performance.class1 to TEXT,
            performance.name1 to TEXT,
            performance.username to TEXT,
            performance.number to INTEGER,
            performance.semester to INTEGER,
            performance.teacher to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(admin.NAME, true)
        db.dropTable(teacher.NAME, true)
        db.dropTable(student.NAME, true)
        db.dropTable(performance.NAME, true)
        db.dropTable(depart.NAME, true)
        db.dropTable(classlist.NAME, true)
        db.dropTable(semester.NAME,true)
        db.dropTable(speciality.NAME,true)
        onCreate(db)
    }

    //删除符合条件的
    fun delete(condition: String, TABLE_NAME: String): Int {
        var count = 0
        use { count = delete(TABLE_NAME, condition, null) }
        return count
    }

    //添加一条记录
    fun insert(TABLE_NAME: String, values: ContentValues) {
        use {
            insert(TABLE_NAME, null, values)
            Log.d("sucess" ,"su")
        }
    }

    //更新数据
    fun update(TABLE_NAME: String, condition: String, values: ContentValues) {
        use { update(TABLE_NAME, values, condition, null) }
    }
    //查询学期信息
    fun querySemester(condition: String):String{
        val sql="select id,name1 from semester where $condition;"
        Log.d("sql", "query sql :$sql")
        var semesterList= mutableListOf<Semester>()
        use {
            var cursor=readableDatabase.rawQuery(sql,null)
            if (cursor.moveToFirst()){
                while (true){
                    val semester= Semester(
                        id=cursor.getInt(0),
                        name1 = cursor.getString(1)
                    )
                    semesterList.add(semester)
                    if (cursor.isLast)
                        break
                    cursor.moveToNext()
                }
            }
            cursor.close()

        }
        return semesterList[0].name1
    }
    //查询管理员信息
    fun queryAdmin(condition: String): List<Admin> {
        val sql = "select id,username,password,name1 from admin where $condition;"
        Log.d("sql", "query sql :$sql")
        var adminArray = mutableListOf<Admin>()
        Log.d("sql", "0")
        use {
            var cursor = readableDatabase.rawQuery(sql, null)



            if (cursor.moveToFirst()) {

                while (true) {
                    Log.d("sql", "${cursor.getInt(0)}")
                    val admin = Admin(
                        id = cursor.getInt(0),
                        username = cursor.getString(1),
                        password = cursor.getString(2),
                        name1 = cursor.getString(3)

                    )
                    Log.d("sql", "${cursor.getString(3)}")
                    adminArray.add(admin)
                    if (cursor.isLast) {
                        break
                    }
                    cursor.moveToNext()
                }
            }
            cursor.close()
        }
        return adminArray
    }

    //查询学生信息
    //查询管理员信息
    fun queryStudent(condition: String): List<Student> {
        val sql = "select id,name1,username,password,sex,depart,speciality,class1 from student where $condition;"
        Log.d("sql", "query sql :$sql")
        var studentArray = mutableListOf<Student>()
        Log.d("sql", "0")
        use {
            var cursor = readableDatabase.rawQuery(sql, null)

            if (cursor.moveToFirst()) {

                while (true) {
                    Log.d("sql", "${cursor.getInt(0)}")
                    val student = Student(
                        id = cursor.getInt(0),
                        name1 = cursor.getString(1),
                        username = cursor.getString(2),
                        password = cursor.getString(3),
                        sex = cursor.getString(4),
                        depart = cursor.getString(5),
                        speciality = cursor.getString(6),
                        class1 = cursor.getString(7)

                    )
                    Log.d("sql", "${cursor.getString(3)}")
                    studentArray.add(student)
                    if (cursor.isLast) {
                        break
                    }
                    cursor.moveToNext()
                }
            }
            cursor.close()
        }
        return studentArray
    }

    //查询教师信息
    fun queryTeacher(condition: String): List<Teacher> {
        val sql = "select id,name1,username,password,class1,depart,course from teacher where $condition;"
        Log.d("sql", "query sql :$sql")
        var teacherArray = mutableListOf<Teacher>()
        Log.d("sql", "0")
        use {
            var cursor = readableDatabase.rawQuery(sql, null)



            if (cursor.moveToFirst()) {

                while (true) {
                    Log.d("sql", "${cursor.getInt(0)}")
                    val teacher = Teacher(
                        id = cursor.getInt(0),
                        name1 = cursor.getString(1),
                        username = cursor.getString(2),
                        password = cursor.getString(3),
                        class1 = cursor.getString(4),
                        depart = cursor.getString(5),
                        course = cursor.getString(6)

                    )
                    Log.d("sql", "${cursor.getString(3)}")
                    teacherArray.add(teacher)
                    if (cursor.isLast) {
                        break
                    }
                    cursor.moveToNext()
                }
            }
            cursor.close()
        }
        return teacherArray
    }

    //查询成绩信息
    fun queryPerformance(condition: String): List<Performance> {
        val sql = "select course,class1,name1,username,number,semester,teacher from performance where $condition;"
        Log.d("sql", "query sql :$sql")
        var performanceArray = mutableListOf<Performance>()
        Log.d("sql", "0")
        use {
            var cursor = readableDatabase.rawQuery(sql, null)



            if (cursor.moveToFirst()) {

                while (true) {
                    Log.d("sql", "${cursor.getInt(0)}")
                    val performance = Performance(
                        course = cursor.getString(0),
                        class1 = cursor.getString(1),
                        name1 = cursor.getString(2),
                        username = cursor.getString(3),
                        number = cursor.getInt(4),
                        semester = cursor.getInt(5),
                        teacher = cursor.getString(6)
                    )
                    Log.d("sql", "${cursor.getString(3)}")
                    performanceArray.add(performance)
                    if (cursor.isLast) {
                        break
                    }
                    cursor.moveToNext()
                }
            }
            cursor.close()
        }
        return performanceArray
    }

    //查询班级信息
    fun queryClassList(condition: String): List<ClassList> {
        val sql = "select id,name1,speciality from classlist where $condition;"
        Log.d("sql", "query sql :$sql")
        var classListArray = mutableListOf<ClassList>()
        Log.d("sql", "0")
        use {
            var cursor = readableDatabase.rawQuery(sql, null)



            if (cursor.moveToFirst()) {

                while (true) {
                    Log.d("sql", "${cursor.getInt(0)}")
                    val classList = ClassList(
                        id = cursor.getInt(0),
                        name1 = cursor.getString(1),
                        speciality = cursor.getString(2)
                    )
                    Log.d("sql", "${cursor.getString(1)}")
                    classListArray.add(classList)
                    if (cursor.isLast) {
                        break
                    }
                    cursor.moveToNext()
                }
            }
            cursor.close()
        }
        return classListArray
    }
    //查询学生专业信息
    fun querySpeciality(condition: String):List<Speciality>{
        val sql = "select id,name1,depart from speciality where $condition;"
        Log.d("sql", "query sql :$sql")
        var specialityArray = mutableListOf<Speciality>()
        use {
            var cursor = readableDatabase.rawQuery(sql, null)



            if (cursor.moveToFirst()) {

                while (true) {
                    Log.d("sql", "${cursor.getInt(0)}")
                    val speciality = Speciality(
                        id = cursor.getInt(0),
                        name1 = cursor.getString(1),
                        depart = cursor.getString(2)
                    )
                    Log.d("sql", "${cursor.getString(1)}")
                    specialityArray.add(speciality)
                    if (cursor.isLast) {
                        break
                    }
                    cursor.moveToNext()
                }
            }
            cursor.close()
        }
        return specialityArray
    }

    //查询部门信息
    fun queryDepart(condition: String): List<Depart> {
        val sql = "select id,name1,type from depart where $condition;"
        Log.d("sql", "query sql :$sql")
        var departArray = mutableListOf<Depart>()
        Log.d("sql", "0")
        use {
            var cursor = readableDatabase.rawQuery(sql, null)



            if (cursor.moveToFirst()) {

                while (true) {
                    Log.d("sql", "${cursor.getInt(0)}")
                    val depart = Depart(
                        id = cursor.getInt(0),
                        name1 = cursor.getString(1),
                        type = cursor.getInt(2)
                    )
                    Log.d("sql", "${cursor.getString(1)}")
                    departArray.add(depart)
                    if (cursor.isLast) {
                        break
                    }
                    cursor.moveToNext()
                }
            }
            cursor.close()
        }
        return departArray
    }
}