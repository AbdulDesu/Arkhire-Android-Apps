package com.sizdev.arkhirefortalent.homepage.item.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectAdapter
import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectModel
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.approvedproject.ShowApprovedProjectActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.declinedproject.ShowDeclinedProjectActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject.HighLightProjectAdapter
import com.sizdev.arkhirefortalent.homepage.item.home.project.waitingproject.ShowWaitingProjectActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.android.synthetic.main.alert_session_expired.view.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog: AlertDialog
    private lateinit var handler: Handler
    private lateinit var coroutineScope: CoroutineScope

    private var accountID : String ? = null
    private var presenter: HomePresenter? = null

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // Set Service
        setService()

        // Loading Data
        showProgressBar()

        // Set Date
        setDate()

        // Set Up RecyclerView
        setRecyclerView()

        // Get Current Login Data
        getCurrentAccount()

        // Auto Refresh Management
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                if (accountID != null) {
                    presenter?.getAccountName(accountID!!)
                    presenter?.getHighlightedProject(accountID!!)
                }
                handler.postDelayed(this, 2000)
            }
        })


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

    private fun getCurrentAccount() {
        val sharedPrefData = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
        accountID = sharedPrefData.getString("accID", null)
    }

    private fun setService() {
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        val service = activity?.let { ArkhireApiClient.getApiClient(it)?.create(ArkhireApiService::class.java) }
        presenter = HomePresenter(coroutineScope, service)
    }

    private fun setRecyclerView() {
        binding.rvProjectHighlight.adapter = HighLightProjectAdapter()
        binding.rvProjectHighlight.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
    }


    override fun addHighlightProject(list: List<ProjectModel>) {
        (binding.rvProjectHighlight.adapter as HighLightProjectAdapter).addList(list)
    }

    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    private fun setDate() {
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM YYYY")
        val currentDate = dateFormat.format(Date())
        binding.tvHomeDate.text = currentDate
    }

    @SuppressLint("SetTextI18n")
    override fun setGreeting(name: String) {
        // Split The Name
        val nameSplitter = name.split(" ")

        // Get Hour
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]

        if (nameSplitter.size == 1){
            when (timeOfDay) {
                in 0..11 -> binding.tvUserGreeting.text = "Good Morning, ${name.capitalize(Locale.ROOT)}"
                in 12..15 -> binding.tvUserGreeting.text = "Good Afternoon, ${name.capitalize(Locale.ROOT)}"
                in 16..20 -> binding.tvUserGreeting.text = "Good Evening, ${name.capitalize(Locale.ROOT)}"
                in 21..23 -> binding.tvUserGreeting.text = "Good Night, ${name.capitalize(Locale.ROOT)}"
            }
        }

        else {
            val lastName = nameSplitter[1]
            when (timeOfDay){
                in 0..11 -> binding.tvUserGreeting.text = "Good Morning, $lastName"
                in 12..15 -> binding.tvUserGreeting.text = "Good Afternoon, $lastName"
                in 16..20 -> binding.tvUserGreeting.text = "Good Evening, $lastName"
                in 21..23 -> binding.tvUserGreeting.text = "Good Night, $lastName"
            }
        }
    }

    override fun setError(error: String) {
        if (error == "Session Expired !"){
            sessionExpiredAlert()
            dialog.show()
        }
        else {
            binding.emptyHighlight.visibility = View.VISIBLE
        }
    }

    private fun showProgressBar() {
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100
    }

    override fun hideProgressBar() {
        binding.loadingScreen.visibility = View.GONE
    }

    private fun sessionExpiredAlert() {
        handler.removeCallbacksAndMessages(null)
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

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
        presenter = null
    }
}