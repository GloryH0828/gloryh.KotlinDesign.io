package com.example.myapplication.Activity.Admin

import Helper.DBHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_admin_edit_msg.*
import org.jetbrains.anko.toast


class AdminEditMsgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_msg)
        val ID=intent.getIntExtra("ID",0)
        Log.d("ID1112",ID.toString())
        var helper=DBHelper.getInstance(this)
        var adminList=helper.queryAdmin("id = $ID ")
        et_admin_name.setText(adminList[0].name1)
        bt_edit_submit.setOnClickListener {
            if (et_admin_name.text.isEmpty()){
                toast("姓名不得为空")
            }else{
                var name =et_admin_name.text.toString()
                if (name==adminList[0].name1){
                    toast("姓名未改变")
                }else{
                    var values=ContentValues()
                    values.put("name1",name)
                    helper.update("admin","id = $ID",values)
                    toast("修改成功！")
                }
            }
        }

    }
}
