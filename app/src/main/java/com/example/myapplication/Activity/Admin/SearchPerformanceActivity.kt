package com.example.myapplication.Activity.Admin

import Domain.Performance
import Helper.DBHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_search_performance.*
import org.jetbrains.anko.*

class SearchPerformanceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_performance)
        sea_per_layout.visibility=View.INVISIBLE
        var helper=DBHelper.getInstance(this)
        val semesterList= listOf("18191","18192","19201","19202")
        var username=""
        var count=0
        var index1=0
        var semester1=0
        var course1=""
        var newNumber=0
        var performanceList:List<Performance>
        sea_cou_semester.text=semesterList[0]
        sea_cou_semester.setOnClickListener {
            selector("请选择学期",semesterList){_,i->
                sea_cou_semester.text=semesterList[i]

            }
        }
        bt_sea_course.setOnClickListener {
            if (sea_cou_index.text.isEmpty()){
                toast("请输入要查询的学生学号")
            }else{
                username=sea_cou_index.text.toString()
                var student=helper.queryStudent("username = '$username'")
                Log.d("stuCount",student.size.toString())
                if (student.isEmpty()){
                    sea_per_layout.visibility=View.INVISIBLE
                    toast("对不起，未查询到该学生信息")
                }else{
                    performanceList=helper.queryPerformance("username = '$username' and semester = '${sea_cou_semester.text}'")
                    Log.d("perCount",performanceList.size.toString())
                    if (performanceList.isEmpty()){
                        sea_per_layout.visibility=View.INVISIBLE
                        toast("该学生成绩还未录入")
                    }else{
                        sea_per_layout.visibility=View.VISIBLE
                        sea_cou_name.text=student[0].name1
                        sea_cou_username.text=username
                        sea_cou_class.text=student[0].class1
                        semester1=performanceList[0].semester
                        course1=performanceList[0].course
                        index1=0
                        count=performanceList.size
                        getPerformanceInfo(performanceList,count,index1)
                    }
                }
            }
        }
        sea_cou_previous.setOnClickListener {
            index1-=1
            getPerformanceInfo(helper.queryPerformance("username = '$username' and semester = '${sea_cou_semester.text}'"),count,index1)
        }
        sea_cou_next.setOnClickListener {
            index1+=1
            getPerformanceInfo(helper.queryPerformance("username = '$username' and semester = '${sea_cou_semester.text}'"),count,index1)
        }
        bt_sea_cou_edit.setOnClickListener {
            alert("您确定要重置该学生的${sea_cou_course.text}吗？", "成绩修改") {
                yesButton {

                    alert {
                        customView {
                            verticalLayout {
                                //对话框标题
                                toolbar {
                                    lparams(width = matchParent, height = wrapContent)
                                    backgroundColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                                    title = "成绩修改"
                                }
                                 val editNumber= editText {
                                    hint = "请输入新成绩"
                                    padding = dip(20)
                                }
                                positiveButton("确认") {
                                    var ok=false
                                    if(editNumber.text.toString().isEmpty()) {
                                        toast("修改失败，成绩输入为空")
                                    }else {
                                         newNumber=editNumber.text.toString().toInt()


                                        Log.d("newNumber",semester1.toString()+course1+newNumber.toString())
                                        var values=ContentValues()
                                        values.put("number",newNumber)
                                        helper.update("performance","username='$username' and course='$course1' and semester = '$semester1'",values)
                                        toast("修改成功")
                                        ok=true
                                    }
                                    if (ok){
                                        sea_cou_number.text=newNumber.toString()

                                    }
                                }
                            }
                        }



                    }.show()

                }
                noButton {
                    toast("取消修改")
                }
            }.show()
        }
    }

    private fun getPerformanceInfo(performanceList: List<Performance>, count: Int, index: Int) {
        var course=performanceList[index].course
        var number=performanceList[index].number
        sea_cou_course.text=course
        sea_cou_number.text=number.toString()
        sea_cou_count.text="${index+1} / $count"
        if (index==0){
            sea_cou_previous.visibility=View.INVISIBLE
        }else{
            sea_cou_previous.visibility=View.VISIBLE
        }
        if (index==count-1){
            sea_cou_next.visibility=View.INVISIBLE
        }else{
            sea_cou_next.visibility=View.VISIBLE
        }
    }
}
