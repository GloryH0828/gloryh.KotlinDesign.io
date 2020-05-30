package com.example.myapplication.Activity.Admin

import Domain.Teacher
import Helper.DBHelper
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_search_source.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class SearchSourceActivity : AppCompatActivity() {

    private var BEGIN=false
    private var END=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_source)
        sea_c_layout.visibility=View.INVISIBLE
        var helper=DBHelper.getInstance(this)
        val searchKey= listOf("教师","名称","班级")
        var index=""
        var count=0
        var index1=0
        var type=""
        var teacherList:List<Teacher>
        sea_c_key.text=searchKey[0]
        sea_c_key.setOnClickListener {
            selector("请选择查询类型",searchKey){_,i->
                sea_c_key.text=searchKey[i]
            }
        }
        bt_sea_c_search.setOnClickListener {
            if (sea_c_index.text.isEmpty()){
                toast("请输入关键字")
            }else{
                 type=when(sea_c_key.text){
                    "教师"->"name1"
                    "名称"->"course"
                    else ->"class1"
                }
                count=0

                index=sea_c_index.text.toString()
                teacherList=helper.queryTeacher("$type = '$index'")
                count=teacherList.size
                if (count==0){
                    toast("未查询到相关信息")
                }else{
                    index1=0
                    getCourseInfo(teacherList,count,index1)
                    sea_c_layout.visibility=View.VISIBLE
                }
            }
        }
        sea_c_previous.setOnClickListener {
            if(BEGIN){
                toast("前面没有了")
            }else{
                index1-=1
                getCourseInfo(helper.queryTeacher("$type = '$index'"),count,index1)
            }
        }
        sea_c_next.setOnClickListener {
            if (END){
                toast("后面没有了")
            }else{
                index1+=1
                getCourseInfo(helper.queryTeacher("$type = '$index'"),count,index1)
            }
        }
    }

    private fun getCourseInfo(teacherList: List<Teacher>, count: Int, index1: Int) {
        sea_c_name.text=teacherList[index1].course
        sea_c_teacher.text=teacherList[index1].name1
        sea_c_class.text=teacherList[index1].class1
        sea_c_count.text="${index1+1} / $count"
        var performance=DBHelper.getInstance(this).queryPerformance("class1 = '${teacherList[index1].class1}' and course = '${teacherList[index1].course}'")
        if (performance.isEmpty()){
            sea_c_performance.text="未录入"

        }else{
            sea_c_performance.text="已录入"
        }
        BEGIN=index1==0
        END=index1==count-1
    }
}
