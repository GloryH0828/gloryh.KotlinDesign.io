package com.example.myapplication.Activity.Student

import Helper.DBHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_student_editmsg.*
import org.jetbrains.anko.toast

class StudentEditmsgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_editmsg)
        val username=intent.getStringExtra("username")
        var helper=DBHelper.getInstance(this)
        val student=helper.queryStudent("username = '$username'")
        Log.d("ID1112",username)
        if(username!=null || username!=""){
            et_stu_name.setText(student[0].name1)
            et_stu_username.text=student[0].username
            when(student[0].sex){
                "男"->rb_stu_male.isChecked=true
                else->rb_stu_female.isChecked=true
            }
            tv_stu_class.text=student[0].class1
            tv_stu_speciality.text=student[0].speciality
        }
        var sex=student[0].sex
        rg_stu_sex.setOnCheckedChangeListener { group, checkedId ->
            sex=when(checkedId){
                R.id.rb_stu_male -> "男"
                else ->"女"
            }
        }
        bt_edit_stu_msg_submit.setOnClickListener {
            var name=et_stu_name.text.toString()
            if (name.isEmpty()){
                toast("姓名不得为空")
            }else{
                var values=ContentValues()
                values.put("name1",name)
                values.put("sex",sex)
                helper.update("student","username = '$username'",values)
                toast("修改成功")
            }
        }
    }

}
