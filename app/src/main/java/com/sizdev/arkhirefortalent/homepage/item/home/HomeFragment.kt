package com.sizdev.arkhirefortalent.homepage.item.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.databinding.FragmentHomeBinding
import com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject.HighLightProjectAdapter
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.approvedproject.ShowApprovedProjectActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.declinedproject.ShowDeclinedProjectActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject.HighLightProjectModel
import com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject.HighLightProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.waitingproject.ShowWaitingProjectActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.android.synthetic.main.alert_session_expired.view.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog: AlertDialog
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ArkhireApiClient.getApiClient(it) }!!.create(ArkhireApiService::class.java)

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        // Get Date
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM YYYY")
        val currentDate = dateFormat.format(Date())

        // Show Current Loged in Account Name
        val sharedPrefData = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
        val accountID = sharedPrefData.getString("accID", null)

        // Data Refresh Management
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                if (accountID != null) {
                    showAccountName(accountID)
                    showNewerProject(accountID)
                }
                mainHandler.postDelayed(this, 1000)
            }
        })


        binding.tvHomeDate.text = currentDate

        binding.rvProjectHighlight.adapter = HighLightProjectAdapter()
        binding.rvProjectHighlight.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

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
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getAccountResponse(accountID)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is HomeResponse) {
                val accountName = result.data[0].accountName

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

            else {
                sessionExpiredAlert()
                dialog.show()
            }
        }
    }

    private fun showNewerProject(accountID: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getNewerProjectResponse(accountID)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is HighLightProjectResponse) {
                val list = result.data?.map{
                    HighLightProjectModel(it.offeringID, it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.projectSallary, it.projectOwner, it.projectOwnerName, it.projectOwnerImage, it.hiringStatus, it.replyMsg, it.repliedAt)
                }

                (binding.rvProjectHighlight.adapter as HighLightProjectAdapter).addList(list)

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }
        }

    }

    private fun sessionExpiredAlert() {
        val view: View = layoutInflater.inflate(R.layout.alert_session_expired, null)

        dialog = activity?.let {
            AlertDialog.Builder(it)
                    .setView(view)
                    .setCancelable(false)
                    .create()
        }!!

        view.bt_okRelog.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(activity, LoginActivity::class.java)
            val sharedPref = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("accID", null)
            editor.apply()
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

}