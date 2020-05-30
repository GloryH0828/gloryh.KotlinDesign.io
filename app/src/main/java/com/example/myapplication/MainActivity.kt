package com.example.myapplication

import Helper.DBHelper
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Activity.Admin.AdminActivity
import com.example.myapplication.Activity.Student.StudentActivity
import com.example.myapplication.Activity.Teacher.TeacherActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var username=username
        var password=password
        var login=login
        var role=role
        var role1=""
        role.setOnCheckedChangeListener{group, checkedId ->
            when(checkedId) {
                R.id.bt_admin ->{
                    role1="admin"
                    toast("您选择的身份为${role1}")
                }
                R.id.bt_student->{
                    role1 = "student"
                    toast("您选择的身份为${role1}")
                }
                R.id.bt_teacher->{
                    role1="teacher"
                    toast("您选择的身份为${role1}")
                }
                else ->{
                    role1=""
                    toast("123123")
                }
            }
        }
        login.setOnClickListener {
            when(true){

                username.text.isEmpty() -> toast("请输入您的账号")
                password.text.isEmpty() -> toast("请输入您的密码")
                else-> {
                    if (role1 == "") {
                        toast("请选择您的相应身份！")
                    }else{
                        //连接数据库，登陆信息比对
                        var helper:DBHelper=DBHelper.getInstance(this)
                        var username1=username.text.toString()
                        var password1=password.text.toString()
                        var loginStatus=0
                        when(role1){
                            "student"->  {
                                val stuUser=helper.queryStudent("username = $username1")
                                //loginStatus=1匹配成功，跳转至相应身份页
                                if (stuUser.isNotEmpty()){
                                    Log.d("sql33",stuUser[0].id.toString())
                                    loginStatus = if(stuUser[0].password==password1) {
                                        startActivity<StudentActivity>(
                                            "stuID" to stuUser[0].id,
                                            "stuUsername" to stuUser[0].username
                                        )
                                        1
                                    } else
                                        -1
                                }
                            }
                            "teacher"->  {
                                val teaUser=helper.queryTeacher("username = $username1")
                                if (teaUser.isNotEmpty()){
                                    loginStatus = if(teaUser[0].password==password1){
                                        startActivity<TeacherActivity>(
                                            "teaID" to teaUser[0].id,
                                            "teaUsername" to teaUser[0].username
                                        )
                                        1
                                    } else
                                        -1
                                }
                            }
                            else -> {
                                val admUser=helper.queryAdmin("username = $username1")
                                if (admUser.isNotEmpty()){
                                    loginStatus = if(admUser[0].password==password1){
                                        startActivity<AdminActivity>("admID" to admUser[0].id)
                                        1
                                    } else
                                        -1
                                }
                            }
                        }



                        when(loginStatus){
                            -1-> toast("密码错误，请重新输入！")
                            1->toast("登陆成功")
                            else ->toast("该用户不存在，请检查您的账号")
                        }

                        //失败，返回错误信息

                    }
                }
            }
        }

    }




}
