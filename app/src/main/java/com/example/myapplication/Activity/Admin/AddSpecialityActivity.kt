package com.example.myapplication.Activity.Admin

import Helper.DBHelper
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_add_speciality.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class AddSpecialityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_speciality)
        var helper=DBHelper.getInstance(this)
        var departList=helper.queryDepart("type = 1")

        var depart= mutableListOf<String>()
        for (index in departList.indices){
            Log.d("depart",departList[index].name1)
            depart.add(departList[index].name1)
        }
        sel_dpt.text=depart[0]
        sel_dpt.setOnClickListener {
            selector("请选择系别",depart){_,i ->
                sel_dpt.text=depart[i]

            }
        }
        add_spe.setOnClickListener {
            if (et_spe.text.isEmpty()){
                toast("请输入要添加的专业名")
            }else{
                var speciality=et_spe.text.toString()
                var spList=helper.querySpeciality("name1 = '$speciality'")
                if (spList.isNotEmpty()){
                    toast("该专业已存在！")
                }else{
                    var values=ContentValues()
                    values.put("depart",sel_dpt.text.toString())
                    values.put("name1",speciality)
                    helper.insert("speciality",values)
                    toast("添加成功！")
                }
            }
        }
    }
}
