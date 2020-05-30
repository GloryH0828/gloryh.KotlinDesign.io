package com.example.myapplication.Fragment.Student


import Domain.Performance
import Helper.DBHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_student_manager.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.selector


/**
 * A simple [Fragment] subclass.
 */
class StudentManagerFragment : Fragment() {
    var username:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("username",username)
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_student_manager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val helper= DBHelper.getInstance(ctx,0)
        val performance=helper.queryPerformance("username = $username")
        val datelist= listOf("19202","19201","18191","18192")
        tv_spinner.text=datelist[0]
        performance_list.text=getPerformance(performance,tv_spinner.text.toString().toInt())
        tv_spinner.setOnClickListener {
           selector("请选择学期",datelist){_,i->
               tv_spinner.text=datelist[i]
               performance_list.text=getPerformance(performance,tv_spinner.text.toString().toInt())
           }
        }
    }


    private fun getPerformance(performance: List<Performance>, semester: Int): String {
        var performancelist:String="该学期各科成绩如下：\n"
        if (performance.isNotEmpty()){
            for (i in performance.indices){
                if(performance[i].semester==semester) {
                   performancelist= performancelist.plus("${performance[i].course}:${performance[i].number}\n")
                }
            }
        }
        return performancelist
    }


}
