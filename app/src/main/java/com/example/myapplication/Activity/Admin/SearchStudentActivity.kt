package com.example.myapplication.Activity.Admin

import Domain.Student
import Helper.DBHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Activity.Student.StudentEditmsgActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_search_student.*
import org.jetbrains.anko.*

class SearchStudentActivity : AppCompatActivity() {

    private var helper=DBHelper.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_student)
        var searchKey= listOf("姓名","班级","学号")
        sea_stu_key.text=searchKey[0]
        sea_stu_key.setOnClickListener {
            selector("请选择查找类型",searchKey){_,i->
                sea_stu_key.text=searchKey[i]
            }
        }
        bt_sea_stu_next.visibility= View.INVISIBLE
        bt_sea_stu_previous.visibility= View.INVISIBLE
        sea_stu_edit.visibility=View.INVISIBLE
        sea_stu_reset.visibility=View.INVISIBLE
        sea_stu_delete.visibility=View.INVISIBLE
        var number =0
        var count=0
        var studentList:List<Student>
        var type=""
        var index=""
        bt_sea_stu.setOnClickListener {

           if (sea_stu_index.text.isEmpty()){
               toast("请输入搜索关键字")
           }else{
                number=0
               sea_stu_name.text=""
               sea_stu_username.text=""
               sea_stu_sex.text=""
               sea_stu_class.text=""
               sea_stu_count.text=""
                type = when(sea_stu_key.text.toString()){
                   "姓名"->"name1"
                   "班级"->"class1"
                   else->"username"
               }
                index=sea_stu_index.text.toString()
               studentList=helper.queryStudent("$type like '%$index%'")
               count=studentList.size
               Log.d("count",count.toString())
               if (count!=0){
                   getStudentInfo(studentList,count,number)
                   sea_stu_edit.visibility=View.VISIBLE
                   sea_stu_reset.visibility=View.VISIBLE
                   sea_stu_delete.visibility=View.VISIBLE
               }else{
                   bt_sea_stu_next.visibility= View.INVISIBLE
                   bt_sea_stu_previous.visibility= View.INVISIBLE
                   sea_stu_edit.visibility=View.INVISIBLE
                   sea_stu_reset.visibility=View.INVISIBLE
                   sea_stu_delete.visibility=View.INVISIBLE
                   toast("没有查询到相关学生信息")
               }
           }

        }
        bt_sea_stu_next.setOnClickListener {
            number += 1
            getStudentInfo(helper.queryStudent("$type like '%$index%'"),count,number)
        }
        bt_sea_stu_previous.setOnClickListener {
            number -= 1
            getStudentInfo(helper.queryStudent("$type like '%$index%'"),count,number)
        }
        sea_stu_reset.setOnClickListener {
            alert("您确定要重置该用户的密码吗？", "密码重置") {
                yesButton {
                    var username1=sea_stu_username.text.toString()
                    var values=ContentValues()
                    values.put("password","000000")
                    helper.update("student","username = '$username1'",values)
                    toast("已将该用户密码重置")
                }
                noButton {
                    toast("取消密码重置")
                }
            }.show()
        }
        sea_stu_delete.setOnClickListener {
            alert("您确定要删除该学生的信息吗？", "信息删除") {
                yesButton {
                    var username1=sea_stu_username.text.toString()
                    helper.delete("username = '$username1'","student")
                    bt_sea_stu_next.visibility= View.INVISIBLE
                    bt_sea_stu_previous.visibility= View.INVISIBLE
                    sea_stu_edit.visibility=View.INVISIBLE
                    sea_stu_reset.visibility=View.INVISIBLE
                    sea_stu_delete.visibility=View.INVISIBLE
                    sea_stu_name.text=""
                    sea_stu_username.text=""
                    sea_stu_sex.text=""
                    sea_stu_class.text=""
                    sea_stu_count.text=""
                    toast("已将该学生相关信息删除")
                }
                noButton {
                    toast("信息删除取消")
                }
            }.show()
        }
        sea_stu_edit.setOnClickListener {
            var username1=sea_stu_username.text.toString()
            startActivity<StudentEditmsgActivity>(
                "username" to username1
            )
        }
    }

    private fun getStudentInfo(studentList: List<Student>, count: Int, number: Int) {
        sea_stu_name.text=studentList[number].name1
        sea_stu_username.text=studentList[number].username
        sea_stu_sex.text=studentList[number].sex
        sea_stu_class.text=studentList[number].class1
        sea_stu_count.text="${number+1} / $count"
        if (number!=0){
            bt_sea_stu_previous.visibility= View.VISIBLE
        }else{
            bt_sea_stu_previous.visibility= View.INVISIBLE
        }
        if (number!=count-1){
            bt_sea_stu_next.visibility= View.VISIBLE
        }else{
            bt_sea_stu_next.visibility= View.INVISIBLE
        }
    }
}
