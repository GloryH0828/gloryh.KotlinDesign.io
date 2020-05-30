package com.example.myapplication.Fragment.Admin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.Activity.Admin.*
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_admin_manager.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class AdminManagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_manager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add_stu.setOnClickListener {
            startActivity<AddStudentActivity>()
        }
        add_tea.setOnClickListener {
            startActivity<AddTeacherActivity>()
        }
        search_stu.setOnClickListener {
            startActivity<SearchStudentActivity>()
        }
        search_tea.setOnClickListener {
            startActivity<SearchTeacherActivity>()
        }
        search_performance.setOnClickListener {
            startActivity<SearchPerformanceActivity>()
        }
        search_source.setOnClickListener {
            startActivity<SearchSourceActivity>()
        }
    }


}
