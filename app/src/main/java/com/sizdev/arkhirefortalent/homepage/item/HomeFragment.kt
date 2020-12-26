package com.sizdev.arkhirefortalent.homepage.item

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentHomeBinding
import com.sizdev.arkhirefortalent.homepage.HighLightProjectAdapter
import com.sizdev.arkhirefortalent.homepage.project.allproject.ShowAllProjectActivity
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // Get Date
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM YYYY")
        val currentDate = dateFormat.format(Date())

        // Get Saved name
        val sharedPrefData = requireActivity().getSharedPreferences("fullData", Context.MODE_PRIVATE)
        val savedData = sharedPrefData.getString("fullName", null)
        val nameSplitter = savedData?.split(" ")

        // Get Hour
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]


        binding.tvHomeDate.text = currentDate

        binding.tvHomeDate.text = currentDate
        if (nameSplitter?.size == 1){
            if (timeOfDay in 0..11) {
                binding.tvUserGreeting.text = "Good Morning, ${savedData.capitalize()}"
            } else if (timeOfDay in 12..15) {
                binding.tvUserGreeting.text = "Good Afternoon, ${savedData.capitalize()}"
            } else if (timeOfDay in 16..20) {
                binding.tvUserGreeting.text = "Good Evening, ${savedData.capitalize()}"
            } else if (timeOfDay in 21..23) {
                binding.tvUserGreeting.text = "Good Night, ${savedData.capitalize()}"
            }
        }
        else {
            val lastName = nameSplitter?.get(1).toString()
            if (timeOfDay in 0..11) {
                binding.tvUserGreeting.text = "Good Morning, $lastName"
            } else if (timeOfDay in 12..15) {
                binding.tvUserGreeting.text = "Good Afternoon, $lastName"
            } else if (timeOfDay in 16..20) {
                binding.tvUserGreeting.text = "Good Evening, $lastName"
            } else if (timeOfDay in 21..23) {
                binding.tvUserGreeting.text = "Good Night, $lastName"
            }
        }

        binding.rvProject.adapter = HighLightProjectAdapter()
        binding.rvProject.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        binding.ivNotifBell.setOnClickListener {
            val intent = Intent(activity, ShowAllProjectActivity::class.java)
            startActivity(intent)
        }



        return binding.root
    }

}