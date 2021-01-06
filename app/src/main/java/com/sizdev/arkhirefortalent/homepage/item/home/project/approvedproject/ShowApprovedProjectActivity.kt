package com.sizdev.arkhirefortalent.homepage.item.home.project.approvedproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityShowApprovedProjectBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*

class ShowApprovedProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowApprovedProjectBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_approved_project)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ArkhireApiClient.getApiClient(this)!!.create(ArkhireApiService::class.java)


        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        setSupportActionBar(binding.tbShowApprovedProject)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.rvShowApprovedProject.adapter = ShowApprovedProjectAdapter()
        binding.rvShowApprovedProject.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.tbShowApprovedProject.setNavigationOnClickListener {
            finish()
        }

        showApprovedProject()
    }

    private fun showApprovedProject() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getApprovedProjectResponse()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ShowApprovedProjectResponse) {
                val list = result.data?.map{
                    ShowApprovedProjectModel(it.offeringID, it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.projectSallary, it.hiringStatus, it.replyMsg, it.repliedAt)
                }

                (binding.rvShowApprovedProject.adapter as ShowApprovedProjectAdapter).addList(list)

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