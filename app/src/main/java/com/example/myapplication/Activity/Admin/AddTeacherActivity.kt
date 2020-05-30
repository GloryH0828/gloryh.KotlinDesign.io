package com.example.myapplication.Activity.Admin

import Domain.ClassList
import Helper.DBHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_add_teacher.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class AddTeacherActivity : AppCompatActivity() {
    private var helper=DBHelper.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_teacher)
        var departList=helper.queryDepart("1=1")
        var depart= mutableListOf<String>()
        for (index in departList.indices){
            depart.add(departList[index].name1)
            Log.d("depart",depart[index])
        }
        teadepart_list.text=depart[0]
        getClassList(teadepart_list.text as String)
        teadepart_list.setOnClickListener {
            selector("请选择部门",depart){_,i->
                teadepart_list.text=depart[i]
                getClassList(teadepart_list.text as String)
            }
        }
        bt_add_tea.setOnClickListener {
            if (edit_tea_username.text.isEmpty()){
                toast("请输入教师工号")
            }else{
                if (et_tea_name.text.isEmpty()){
                    toast("请输入教师姓名")
                }else{
                    if (et_tea_course.text.isEmpty()){
                        toast("请输入该教师教授的科目")
                    }else{
                        var depart1=teadepart_list.text.toString()
                        var class11=tea_classlist.text.toString()
                        var username=edit_tea_username.text.toString()
                        var name=et_tea_name.text.toString()
                        var course=et_tea_course.text.toString()
                        var values=ContentValues()
                        values.put("depart",depart1)
                        values.put("class1",class11)
                        values.put("name1",name)
                        values.put("username",username)
                        values.put("course",course)
                        values.put("password","000000")
                        helper.insert("teacher",values)
                        toast("教师添加成功")
                    }
                }
            }
        }

    }

    private fun getClassList(depart: String) {
        var classList= mutableListOf<ClassList>()
        var class1= mutableListOf<String>()
        if (depart == "基础学科部"){
            classList= helper.queryClassList("1=1").toMutableList()
        }else{
            var specialityList=helper.querySpeciality("depart = '$depart' ")
            for (index in specialityList.indices){
                Log.d("speciality",specialityList[index].name1)
                var list=helper.queryClassList("speciality = '${specialityList[index].name1}'")
                for (index in list.indices){
                    Log.d("class111",list[index].name1)
                    classList.add(list[index])
                }

            }

        }

        for (index in classList.indices){
            class1.add(classList[index].name1)
            Log.d("class111",class1[index])
        }
        tea_classlist.text=class1[0]
        tea_classlist.setOnClickListener {
            selector("请选择班级",class1){_,i->
                tea_classlist.text=class1[i]
            }
        }
    }
}
