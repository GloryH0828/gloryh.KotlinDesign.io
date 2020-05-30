package com.example.myapplication.Activity.Teacher

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.Fragment.Teacher.TeacherManagerFragment
import com.example.myapplication.Fragment.Teacher.TeacherSearchPerformanceFragment
import com.example.myapplication.Fragment.Teacher.TeacherSettingFragment
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_teacher.*

class TeacherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)
        var teaID=intent.getIntExtra("teaID",0)
        var teaUsername=intent.getStringExtra("teaUsername")
        Log.d("sql2","$teaUsername")
        tea_viewPager2.adapter=object: FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                //定义导航页文件数量
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0-> TeacherManagerFragment().also { it.teausername=teaUsername }
                    1-> TeacherSearchPerformanceFragment().also { it.username=teaUsername }
                    else -> TeacherSettingFragment().also{it.ID=teaID}
                }
            }
        }
        TabLayoutMediator(teaTabLayout,tea_viewPager2){tab, position ->
            when(position){
                0->tab.text="成绩录入"
                1->tab.text="成绩查询"
                else->tab.text="设置"
            }
        }.attach()
    }
}
