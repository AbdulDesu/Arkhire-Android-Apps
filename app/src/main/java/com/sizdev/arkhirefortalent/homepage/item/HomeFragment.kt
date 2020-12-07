package com.sizdev.arkhirefortalent.homepage.item

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.homepage.ProjectAdapter
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        // Get Date
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM YYYY")
        val currentDate = dateFormat.format(Date())

        // Get Saved name
        val sharedPrefData = requireActivity().getSharedPreferences("fullData", Context.MODE_PRIVATE)
        val savedData = sharedPrefData.getString("fullName", null)
        val nameSplitter = savedData?.split(" ")
        val lastName = nameSplitter?.get(1).toString()

        // Get Hour
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]

        if (timeOfDay in 0..11) {
            view.tv_userGreeting.text = "Good Morning $lastName"
        } else if (timeOfDay in 12..15) {
            view.tv_userGreeting.text = "Good Afternoon $lastName"
        } else if (timeOfDay in 16..20) {
            view.tv_userGreeting.text = "Good Evening $lastName"
        } else if (timeOfDay in 21..23) {
            view.tv_userGreeting.text = "Good Night $lastName"
        }

        view.tv_homeDate.text = currentDate

        val project = listOf(
                R.drawable.ic_project_android,
                R.drawable.ic_project_android,
                R.drawable.ic_project_android,
                R.drawable.ic_project_android,
                R.drawable.ic_project_android,
                R.drawable.ic_project_android
        )
        val wormDotsIndicator: WormDotsIndicator = view.worm_dots_indicator
        val adapter = ProjectAdapter(project)
        view.vp_project.adapter = adapter
        wormDotsIndicator.setViewPager2(view.vp_project)
        return view
    }

}