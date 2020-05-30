package com.example.myapplication.Activity.Teacher

import Helper.DBHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_teacher_edit_msg.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class TeacherEditMsgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_edit_msg)
        val username=intent.getStringExtra("username")
        Log.d("teacherUsername",username)
        var helper=DBHelper.getInstance(this)
        var teacher=helper.queryTeacher("username = '$username'")
        et_tea_edit_msg_name.setText(teacher[0].name1)
        tv_tea_edit_msg_username.text=teacher[0].username
        tv_tea_edit_msg_depart.text=teacher[0].depart
        var courseList=""
        for (i in teacher.indices){
            courseList+="${teacher[i].class1}-${teacher[i].course}\n"
        }
        tv_tea_edit_msg_course.text=courseList
        bt_tea_edit_msg_submit.setOnClickListener {
            var name=et_tea_edit_msg_name.text.toString()
            if (name.isEmpty()){
                toast("姓名不能为空")
            }else{
                if(name==teacher[0].name1){
                    toast("信息未改变")
                }else{
                    alert("确认修改信息吗？","修改提示"){
                        yesButton {
                            var values=ContentValues()
                            values.put("name1",name)
                            helper.update("teacher","username = '$username'",values)
                            toast("修改成功")
                        }
                        noButton {  }
                    }.show()
                }
            }
        }
    }
}
