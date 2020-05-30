package com.example.myapplication.Activity.Student

import Helper.DBHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_admin_edit_pwd.*
import org.jetbrains.anko.*

class StudentEditPwdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_edit_pwd)
        val ID=intent.getIntExtra("ID",0)
        Log.d("ID1112",ID.toString())
        var helper= DBHelper.getInstance(this)
        bt_edit_admin_pwd_submit.setOnClickListener {
            if (edit_admin_old_pwd.text.isEmpty()){
                toast("请输入旧密码")
            }else{
                if (edit_admin_new_pwd.text.isEmpty()){
                    toast("请输入新密码")
                }else{
                    if (edit_admin_new_pwd_again.text.isEmpty()){
                        toast("请在此输入新密码")
                    }else{
                        var oldPassword=edit_admin_old_pwd.text.toString()
                        var newPassword=edit_admin_new_pwd.text.toString()
                        var newPassword1=edit_admin_new_pwd.text.toString()
                        if (newPassword!=newPassword1){
                            toast("两次新密码输入不一致")
                        }else{
                            var adminList=helper.queryStudent("id = ID")
                            if (oldPassword==adminList[0].password){
                                alert("您确定要交那个密码修改成-$newPassword?", "密码修改") {
                                    yesButton {
                                        var values= ContentValues()
                                        values.put("password",newPassword)
                                        helper.update("student","id = $ID",values)
                                        alert("密码修改成功，请重新登录", "修改成功") {

                                            startActivity<MainActivity>()

                                        }.show()
                                    }
                                    noButton {
                                        toast("取消修改")
                                    }
                                }.show()
                            }else{
                                toast("旧密码输入错误")
                            }
                        }
                    }
                }
            }
        }
    }
}
