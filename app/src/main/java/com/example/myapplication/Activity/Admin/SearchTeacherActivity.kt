package com.example.myapplication.Activity.Admin

import Domain.Teacher
import Helper.DBHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Activity.Teacher.TeacherEditMsgActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_search_teacher.*
import org.jetbrains.anko.*

class SearchTeacherActivity : AppCompatActivity() {
    private var helper=DBHelper.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search_teacher)
        var searchKey= listOf("班级","部门","姓名","工号","课程")
        sea_tea_key.text=searchKey[0]
        sea_tea_key.setOnClickListener {
            selector("请选择查询类型",searchKey){_,i->
                sea_tea_key.text=searchKey[i]
            }
        }
        bt_sea_tea_next.visibility= View.INVISIBLE
        bt_sea_tea_previous.visibility= View.INVISIBLE
        sea_tea_edit.visibility= View.INVISIBLE
        sea_tea_reset.visibility= View.INVISIBLE
        sea_tea_delete.visibility= View.INVISIBLE
        var number =0
        var count=0
        var teacherList:List<Teacher>
        var type=""
        var index=""
        bt_sea_tea.setOnClickListener {
            if (sea_tea_index.text.isEmpty()){
                toast("请输入搜索关键字")
            }else{
                number=0
                sea_tea_name.text=""
                sea_tea_username.text=""
                sea_tea_depart.text=""
                sea_tea_course.text=""
                sea_tea_class.text=""
                sea_tea_count.text=""
                type = when(sea_tea_key.text.toString()){
                    "姓名"->"name1"
                    "班级"->"class1"
                    "部门"->"depart"
                    "课程"->"course"
                    else->"username"
                }
                index=sea_tea_index.text.toString()
                teacherList=helper.queryTeacher("$type like '%$index%'")
                count=teacherList.size
                Log.d("count",count.toString())
                if (count!=0){
                    getTeacherInfo(teacherList,count,number)
                    sea_tea_edit.visibility=View.VISIBLE
                    sea_tea_reset.visibility=View.VISIBLE
                    sea_tea_delete.visibility= View.VISIBLE
                }else{
                    bt_sea_tea_next.visibility= View.INVISIBLE
                    bt_sea_tea_previous.visibility= View.INVISIBLE
                    sea_tea_edit.visibility=View.INVISIBLE
                    sea_tea_reset.visibility=View.INVISIBLE
                    sea_tea_delete.visibility= View.INVISIBLE
                    toast("没有查询到相关教师信息")
                }
            }
        }
        bt_sea_tea_next.setOnClickListener {
            number += 1
            getTeacherInfo(helper.queryTeacher("$type like '%$index%'"),count,number)
        }
        bt_sea_tea_previous.setOnClickListener {
            number -= 1
            getTeacherInfo(helper.queryTeacher("$type like '%$index%'"),count,number)
        }
        sea_tea_reset.setOnClickListener {
            alert("您确定要重置该用户的密码吗？", "密码重置") {
                yesButton {
                    var username1=sea_tea_username.text.toString()
                    var values= ContentValues()
                    values.put("password","000000")
                    helper.update("teacher","username = '$username1'",values)
                    toast("已将该用户密码重置")
                }
                noButton {
                    toast("取消密码重置")
                }
            }.show()
        }
        sea_tea_edit.setOnClickListener {
            var username1=sea_tea_username.text.toString()
            startActivity<TeacherEditMsgActivity>(
                "username" to username1
            )
        }
        sea_tea_delete.setOnClickListener {
            alert("您确定要删除该教师的信息吗？", "信息删除") {
                yesButton {
                    var username1=sea_tea_username.text.toString()
                    helper.delete("username = '$username1'","teacher")
                    bt_sea_tea_next.visibility= View.INVISIBLE
                    bt_sea_tea_previous.visibility= View.INVISIBLE
                    sea_tea_edit.visibility=View.INVISIBLE
                    sea_tea_reset.visibility=View.INVISIBLE
                    sea_tea_delete.visibility= View.INVISIBLE
                    sea_tea_name.text=""
                    sea_tea_username.text=""
                    sea_tea_depart.text=""
                    sea_tea_course.text=""
                    sea_tea_class.text=""
                    sea_tea_count.text=""
                    toast("已将该教师相关信息删除")
                }
                noButton {
                    toast("信息删除取消")
                }
            }.show()
        }
    }

    private fun getTeacherInfo(teacherList: List<Teacher>, count: Int, number: Int) {
        sea_tea_name.text=teacherList[number].name1
        sea_tea_username.text=teacherList[number].username
        sea_tea_depart.text=teacherList[number].depart
        sea_tea_course.text=teacherList[number].course
        sea_tea_class.text=teacherList [number].class1
        sea_tea_count.text="${number+1} / $count"
        if (number!=0){
            bt_sea_tea_previous.visibility= View.VISIBLE
        }else{
            bt_sea_tea_previous.visibility= View.INVISIBLE
        }
        if (number!=count-1){
            bt_sea_tea_next.visibility= View.VISIBLE
        }else{
            bt_sea_tea_next.visibility= View.INVISIBLE
        }
    }
}
