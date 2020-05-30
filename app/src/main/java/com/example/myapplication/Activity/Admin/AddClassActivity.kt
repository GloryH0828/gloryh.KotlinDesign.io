package com.example.myapplication.Activity.Admin

import Helper.DBHelper
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_add_class.*
import kotlinx.android.synthetic.main.activity_add_speciality.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class AddClassActivity : AppCompatActivity() {
    var helper=DBHelper.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_class)

        var departList=helper.queryDepart("type = 1")
        var depart= mutableListOf<String>()

        for (index in departList.indices){
            depart.add(departList[index].name1)
            Log.d("depart",departList[index].name1)
        }
        sel_dpt_cls.text=depart[0]
        getSpecialityList(sel_dpt_cls.text as String)

        sel_dpt_cls.setOnClickListener {
            selector("请选择系别",depart){_,i->
                sel_dpt_cls.text=depart[i]
                getSpecialityList(sel_dpt_cls.text as String)
            }
        }
        add_class.setOnClickListener {
            if (et_class.text.isEmpty()){
                toast("请输入要添加的班级名")
            }else{
                var class1=et_class.text.toString()
                var spList=helper.queryClassList("name1 = '$class1'")
                if (spList.isNotEmpty()){
                    toast("该班级已存在！")
                }else{
                    var values= ContentValues()
                    values.put("speciality",sel_spe.text.toString())
                    values.put("name1",class1)
                    helper.insert("classlist",values)
                    toast("添加成功！")
                }
            }
        }

    }

    private fun getSpecialityList(text: String) {
        var specialityList=helper.querySpeciality("depart = '$text'")
        var speciality= mutableListOf<String>()
        for (index in specialityList.indices){
            speciality.add(specialityList[index].name1)
            Log.d("speciality",specialityList[index].name1)
        }
        sel_spe.text=speciality[0]
        sel_spe.setOnClickListener {
            selector("请选择相应专业",speciality){_,i->
                sel_spe.text=speciality[i]
            }
        }
    }
}
