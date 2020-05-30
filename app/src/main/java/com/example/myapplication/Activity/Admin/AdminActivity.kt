package com.example.myapplication.Activity.Admin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.Fragment.Admin.AdminManagerFragment
import com.example.myapplication.Fragment.Admin.AdminSettingFragment
import com.example.myapplication.Fragment.Admin.DepartClassFragment
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_admin.*


class AdminActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        //NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.adminSettingFragment))
        var admID=intent.getIntExtra("admID",0)
        Log.d("sql2","$admID")

        viewPager2.adapter=object:FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                //定义导航页文件数量
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0->{
                        AdminManagerFragment().also{
                        }
                    }
                    1->{
                        DepartClassFragment()
                    }
                    else -> {
                        AdminSettingFragment().also {
                           it.ID=admID
                        }

                    }
                }
            }
        }
        TabLayoutMediator(adminTabLayout,viewPager2){tab, position ->
            when(position){
                0->{
                    tab.text="信息管理"

                }
                1->{
                    tab.text="系别管理"
                }
                else->{
                    tab.text="设置"

                    }

            }
        }.attach()



    }

}