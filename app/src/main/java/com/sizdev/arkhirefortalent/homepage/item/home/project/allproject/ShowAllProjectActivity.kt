package com.sizdev.arkhirefortalent.homepage.item.home.project.allproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityShowAllProjectBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*

class ShowAllProjectActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShowAllProjectBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_all_project)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ArkhireApiClient.getApiClient(this)!!.create(ArkhireApiService::class.java)

        // Get Current Loged in Data
        val sharedPrefData = this.getSharedPreferences("Token", Context.MODE_PRIVATE)
        val accountID = sharedPrefData.getString("accID", null)

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        setSupportActionBar(binding.tbShowAllProject)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.rvShowAllProject.adapter = ShowAllProjectAdapter()
        binding.rvShowAllProject.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.tbShowAllProject.setNavigationOnClickListener {
            finish()
        }
        if (accountID != null) {
            showAllProject(accountID)
        }
    }

    private fun showAllProject(accountID: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getAllProjectResponse(accountID)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ShowAllProjectResponse) {
                val list = result.data?.map{
                    ShowAllProjectModel(it.offeringID, it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.projectSallary, it.hiringStatus, it.replyMsg, it.repliedAt)
                }

                (binding.rvShowAllProject.adapter as ShowAllProjectAdapter).addList(list)

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }

            else {
                // Show Error
                binding.notFound.visibility = View.VISIBLE

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}