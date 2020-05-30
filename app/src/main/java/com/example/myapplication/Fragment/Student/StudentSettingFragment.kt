package com.example.myapplication.Fragment.Student


import Helper.DBHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.Activity.Student.StudentEditPwdActivity
import com.example.myapplication.Activity.Student.StudentEditmsgActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.setting_cell.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class StudentSettingFragment : Fragment() {
    var ID=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_student_setting, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var ID2=ID
        var helper=DBHelper.getInstance(ctx,0)
        var student=helper.queryStudent("id = $ID")
        var username=student[0].username
        Log.d("ID1111",ID2.toString())
        tv_edit_msg.setOnClickListener {
            startActivity<StudentEditmsgActivity>("username" to username )
        }
        iv_edit_msg.setOnClickListener {
            startActivity<StudentEditmsgActivity>("username" to username )
        }
        tv_edit_pwd.setOnClickListener {
            startActivity<StudentEditPwdActivity>("ID" to ID2)
        }
        iv_edit_pwd.setOnClickListener {
            startActivity<StudentEditPwdActivity>("ID" to ID2)
        }
        iv_login_out.setOnClickListener {
            startActivity<MainActivity>()
        }
        tv_login_out.setOnClickListener {
            startActivity<MainActivity>()
        }

    }


}
