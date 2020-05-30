package com.example.myapplication.Fragment.Admin


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.Activity.Admin.AddClassActivity
import com.example.myapplication.Activity.Admin.AddSpecialityActivity

import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_depart_class.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class DepartClassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_depart_class, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iv_addclass.setOnClickListener {
            startActivity<AddClassActivity>()
        }
        iv_addspeciality.setOnClickListener {
            startActivity<AddSpecialityActivity>()
        }
    }


}
