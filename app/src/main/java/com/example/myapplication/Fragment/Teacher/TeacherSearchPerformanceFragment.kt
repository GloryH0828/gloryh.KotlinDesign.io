package com.example.myapplication.Fragment.Teacher


import Domain.Performance
import Helper.DBHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_teacher_search_performance.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class TeacherSearchPerformanceFragment : Fragment() {

    var username=""
    private var type=""
    private var index=""
    private var FIRST=false
    private var END=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_search_performance, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("username3",username)
        var performanceList:List<Performance>
        var helper=DBHelper.getInstance(ctx ,0)
        val teacherInfo=helper.queryTeacher("username = '$username'")
        tea_sea_layout.visibility=View.INVISIBLE
        var searchKey= listOf("课程","学号","班级")
        var count=0
        var index1=0
        tea_sea_per_key.text=searchKey[0]
        tea_sea_per_key.setOnClickListener {
            selector("请选择查询类型",searchKey){_,i->
                tea_sea_per_key.text=searchKey[i]
            }
        }
        bt_tae_sea_search.setOnClickListener {

            if (tea_sea_per_index.text.isEmpty()){
                toast("请输入关键字")
            }else{
                type=when(tea_sea_per_key.text.toString()){
                    "课程"->"course"
                    "班级"->"class1"
                    else ->"username"
                }
                index=tea_sea_per_index.text.toString()
                performanceList=helper.queryPerformance("$type = '$index' and teacher = '$username'")
                count=performanceList.size
                Log.d("count",count.toString())
                index1=0
                if (count==0){
                    toast("未查询到相关信息")
                }else{
                    index1 = getPerformanceInfo(performanceList,count,index1)
                    tea_sea_layout.visibility=View.VISIBLE
                }

            }
        }
        tea_sea_per_previous.setOnClickListener {
            if (FIRST){
                toast("这是第一个了")
            }else{
                index1-=1
                getPerformanceInfo(helper.queryPerformance("$type = '$index'"),count,index1)
            }
        }
        tea_sea_per_next.setOnClickListener {
            if (END){
                toast("这是最后一个了")
            }else{
                index1+=1
                getPerformanceInfo(helper.queryPerformance("$type = '$index'"),count,index1)
            }
        }

    }

    private fun getPerformanceInfo(performanceList: List<Performance>, count: Int, index1: Int): Int {
        tea_sea_per_name.text=performanceList[index1].name1
        tea_sea_per_username.text=performanceList[index1].username
        tea_sea_per_class.text=performanceList[index1].class1
        tea_sea_per_course.text=performanceList[index1].course
        tea_sea_per_number.text=performanceList[index1].number.toString()
        tea_sea_per_count.text="${index1+1} / $count"
        FIRST = index1==0
        END=index1==count-1
        return index1
    }


}









