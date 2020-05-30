package com.example.myapplication.Activity.Admin

import Helper.DBHelper
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.android.synthetic.main.activity_add_student.stu_username
import kotlinx.android.synthetic.main.fragment_teacher_manager.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class AddStudentActivity : AppCompatActivity() {
    var helper=DBHelper.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        var sex="男"
        rg_sex.setOnCheckedChangeListener { group, checkedId ->
            sex=when(checkedId){
                R.id.rb_male->"男"
                else ->"女"
            }
        }
        var departList=helper.queryDepart("type =1")
        var depart= mutableListOf<String>()
        for (index in departList.indices){
            depart.add(departList[index].name1)
            Log.d("depart",departList[index].name1)
        }
        studepart_list.text=depart[0]
        getStudentSpeciality(studepart_list.text as String)
        studepart_list.setOnClickListener {
            selector("请选择系别",depart){_,i->
                studepart_list.text=depart[i]
                getStudentSpeciality(studepart_list.text as String)
            }
        }

        add_stu_bt.setOnClickListener {
            if (stu_username.text.isEmpty()){
                toast("请输入学号")
            }else{
                if (edit_stu_name.text.isEmpty()){
                    toast("请输入学生姓名")
                }else{
                    var depart1=studepart_list.text.toString()
                    var speciality1=stu_speciality_list.text.toString()
                    var class11=stu_class_list.text.toString()
                    var username=stu_username.text.toString()
                    var name=edit_stu_name.text.toString()
                    var values=ContentValues()
                    values.put("depart",depart1)
                    values.put("speciality",speciality1)
                    values.put("class1",class11)
                    values.put("name1",name)
                    values.put("username",username)
                    values.put("sex",sex)
                    values.put("password","000000")
                    helper.insert("student",values)
                    toast("添加成功")
                }
            }
        }

    }

    private fun getStudentSpeciality(name: String) {
        var specialityList=helper.querySpeciality("depart = '$name'")
        var speciality= mutableListOf<String>()
        for (index in specialityList.indices){
            speciality.add(specialityList[index].name1)
            Log.d("speciality",speciality[index])
        }
        stu_speciality_list.text=speciality[0]
        getClassList(stu_speciality_list.text as String)
        stu_speciality_list.setOnClickListener {
            selector("请选择专业",speciality){_,i->
                stu_speciality_list.text=speciality[i]
                getClassList(stu_speciality_list.text as String)
            }
        }
    }

    private fun getClassList(name: String) {
        var classList=helper.queryClassList("speciality = '$name'")
        var class1= mutableListOf<String>()
        for (index in classList.indices){
            class1.add(classList[index].name1)
            Log.d("class",class1[index])
        }
        stu_class_list.text=class1[0]
        stu_class_list.setOnClickListener {
            selector("请选择班级",class1){_,i->
                stu_class_list.text=class1[i]
            }
        }
    }

}
