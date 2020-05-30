package com.example.myapplication.Fragment.Teacher


import Domain.Performance
import Domain.Student
import Helper.DBHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_teacher_manager.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton

/**
 * A simple [Fragment] subclass.
 */
class TeacherManagerFragment : Fragment() {
    var teausername:String=""
    private var InsertOver=true
    private var InsertBegin=false
    private var InsertOK=false
    private var semester=""
    private var performanceList= mutableListOf<Performance>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("username",teausername)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_manager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var helper= DBHelper.getInstance(ctx,0)
        class_stulistLayout.visibility=View.INVISIBLE
        var teacherList=helper.queryTeacher("username = $teausername")
        var classList= mutableListOf<String>()
        var courseList= mutableListOf<String>()
        var courseClassList= mutableListOf<String>()
        var studentList= listOf<Student>()

        var semesterList=helper.querySemester("id = 1")
        semester=semesterList
        var class1=""
        var course=""
        var count=0
        var index1=0
        for(index in teacherList.indices){
            courseList.add(teacherList[index].course)
            classList.add(teacherList[index].class1)
            courseClassList.add("${classList[index]}-${courseList[index]}")
            Log.d("classCourse",courseClassList[index])
        }

        course_class_list.text=courseClassList[0]
        class1=classList[0]
        course=courseList[0]
        course_class_list.setOnClickListener {

            selector("请选择课程信息",courseClassList){_,i->
                course_class_list.text=courseClassList[i]
                index1=i
                Log.d("index",index1.toString())
                class1=classList[i]
                course=courseList[i]
            }

        }
        cc_yes.setOnClickListener {
            alert("您确定要开始录入--${courseClassList[index1]}--的相关成绩？", "成绩录入提示") {
                yesButton {

                    var student=helper.queryStudent("class1 = '${classList[index1]}'")
                    Log.d("stu",student.size.toString())

                    var speciality=helper.queryPerformance("username = '${student[0].username}'")
                    if (speciality.isNotEmpty()){
                        toast("对不起，相关成绩及已经录入完毕，如有问题请联系管理员")
                    }else{
                        InsertOver=false
                        class_stulistLayout.visibility=View.VISIBLE
                        Log.d("performance",performanceList.size.toString())
                        Log.d("performance",performanceList.size.toString())
                        studentList=student
                        count=studentList.size-1
                        index1=0
                        Log.d("count1",count.toString())
                        getInsertPerformanceInfo(studentList,index1,count)
                        toast("开始录入")
                    }

                }
                noButton {
                    toast("录入取消")
                }
            }.show()
            Log.d("stuList",studentList.size.toString())
        }
        stu_previous.setOnClickListener {
            InsertOK=false
            if (InsertBegin){
                toast("前面没有学生了")
            }else{
                index1 -= 1
                getInsertPerformanceInfo(studentList,index1,count)
            }
        }
        stu_next.setOnClickListener {

            var username=stu_username.text.toString()
            var name =stu_name.text.toString()
            var number=stu_number.text.toString().toInt()
            var performance=Performance(
                course,class1,username,name,number,semester.toInt(),teausername
            )
            performanceList.add(index1,performance)



            if (InsertOver){
                if(InsertOK){
                    var values=ContentValues()
                    for (i in 0 .. count){
                        values.put("username",performanceList[i].username)
                        values.put("name1",performanceList[i].name1)
                        values.put("number",performanceList[i].number)
                        values.put("semester",performanceList[i].semester)
                        values.put("class1",performanceList[i].class1)
                        values.put("course",performanceList[i].course)
                        values.put("teacher",performanceList[i].teacher)
                        helper.insert("performance",values)
                        values.clear()
                    }
                    toast("录入完成")
                    index1=0
                    class_stulistLayout.visibility=View.INVISIBLE
                    InsertOK=false
                }else{

                    var pfString="\n"
                    for (i in 0 ..  count){
                        pfString += "${performanceList[i].name1}-${performanceList[i].number}\n"
                    }
                    alert("录入信息如下，请确认无误后再次点击完成按钮$pfString", "录入验证") {
                        yesButton {  }
                    }.show()
                    InsertOK=true

                }
            }else{
                index1+=1
                getInsertPerformanceInfo(studentList,index1,count)
            }
        }

    }

    private fun getInsertPerformanceInfo(studentList: List<Student>, index: Int, count: Int) {

        stu_username.text=studentList[index].username
        stu_name.text=studentList[index].name1
        stu_number.text.clear()
        InsertBegin = index==0

        if (index==count){
            stu_next.imageResource=R.drawable.bt_complete
            stu_complete.text="完成"
            InsertOver=true
        }else{
            stu_next.imageResource=R.drawable.bt_next
            stu_complete.text="下一个"
            InsertOver=false
        }

    }

}
