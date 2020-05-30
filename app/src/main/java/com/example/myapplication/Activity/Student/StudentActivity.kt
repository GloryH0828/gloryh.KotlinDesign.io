package com.example.myapplication.Activity.Student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.Fragment.Student.StudentManagerFragment
import com.example.myapplication.Fragment.Student.StudentSettingFragment
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        var stuID=intent.getIntExtra("stuID",0)
        var stuUsername=intent.getStringExtra("stuUsername")
        Log.d("sql3","$stuID")
        viewPager2_stu.adapter=object: FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                //定义导航页文件数量
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0-> StudentManagerFragment().also { it.username=stuUsername }
                    else -> StudentSettingFragment().also { it.ID=stuID }
                }
            }
        }
        TabLayoutMediator(stuTabLayout,viewPager2_stu){tab, position ->
            when(position){
                0->tab.text="我的成绩"
                else->tab.text="设置"
            }
        }.attach()
    }
}
