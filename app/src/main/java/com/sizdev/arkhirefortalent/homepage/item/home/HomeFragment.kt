package com.sizdev.arkhirefortalent.homepage.item.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentHomeBinding
import com.sizdev.arkhirefortalent.homepage.item.company.SearchCompanyAdapter
import com.sizdev.arkhirefortalent.homepage.item.company.SearchCompanyModel
import com.sizdev.arkhirefortalent.homepage.item.company.SearchCompanyResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject.HighLightProjectAdapter
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.approvedproject.ShowApprovedProjectActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.declinedproject.ShowDeclinedProjectActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.waitingproject.ShowWaitingProjectActivity
import com.sizdev.arkhirefortalent.networking.ApiClient
import kotlinx.coroutines.*
import retrofit2.http.GET
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: HomeApiService

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ApiClient.getApiClient(it) }!!.create(HomeApiService::class.java)

        // Get Date
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM YYYY")
        val currentDate = dateFormat.format(Date())

        // Show Current Loged in Account Name
        val sharedPrefData = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
        val accountID = sharedPrefData.getString("accID", null)
        if (accountID != null) {
            showAccountName(accountID)
        }

        binding.tvHomeDate.text = currentDate


        binding.rvProject.adapter = HighLightProjectAdapter()
        binding.rvProject.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        binding.lnProjectList.setOnClickListener {
            val intent = Intent(activity, ShowAllProjectActivity::class.java)
            startActivity(intent)
        }

        binding.ivApproved.setOnClickListener {
            val intent = Intent(activity, ShowApprovedProjectActivity::class.java)
            startActivity(intent)
        }

        binding.ivRejected.setOnClickListener {
            val intent = Intent(activity, ShowDeclinedProjectActivity::class.java)
            startActivity(intent)
        }

        binding.ivWaiting.setOnClickListener {
            val intent = Intent(activity, ShowWaitingProjectActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun showAccountName(accountID : String) {
        coroutineScope.launch {
            Log.d("Arkhire Talent", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("Arkhire Talent", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getAccountResponse(accountID)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is HomeResponse) {
                Log.d("Arkhire Talent", result.toString())
                val accountName = result.data.accountName

                // Split The Name
                val nameSplitter = accountName?.split(" ")

                // Get Hour
                val c = Calendar.getInstance()
                val timeOfDay = c[Calendar.HOUR_OF_DAY]

                if (nameSplitter?.size == 1){
                    when (timeOfDay) {
                        in 0..11 -> binding.tvUserGreeting.text = "Good Morning, ${accountName.capitalize()}"
                        in 12..15 -> binding.tvUserGreeting.text = "Good Afternoon, ${accountName.capitalize()}"
                        in 16..20 -> binding.tvUserGreeting.text = "Good Evening, ${accountName.capitalize()}"
                        in 21..23 -> binding.tvUserGreeting.text = "Good Night, ${accountName.capitalize()}"
                    }
                }
                else {
                    val lastName = nameSplitter?.get(1).toString()
                    when (timeOfDay){
                        in 0..11 -> binding.tvUserGreeting.text = "Good Morning, $lastName"
                        in 12..15 -> binding.tvUserGreeting.text = "Good Afternoon, $lastName"
                        in 16..20 -> binding.tvUserGreeting.text = "Good Evening, $lastName"
                        in 21..23 -> binding.tvUserGreeting.text = "Good Night, $lastName"
                    }
                }
            }

        }
    }

}