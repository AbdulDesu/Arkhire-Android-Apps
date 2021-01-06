package com.sizdev.arkhirefortalent.homepage.item.home.project.waitingproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityShowWaitingProjectBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*

class ShowWaitingProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowWaitingProjectBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_waiting_project)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ArkhireApiClient.getApiClient(this)!!.create(ArkhireApiService::class.java)


        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        setSupportActionBar(binding.tbShowWaitingProject)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.rvShowWaitingProject.adapter = ShowWaitingProjectAdapter()
        binding.rvShowWaitingProject.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.tbShowWaitingProject.setNavigationOnClickListener {
            finish()
        }

        showWaitingProject()
    }

    private fun showWaitingProject() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getWaitingProjectResponse()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ShowWaitingProjectResponse) {
                val list = result.data?.map{
                    ShowWaitingProjectModel(it.offeringID, it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.projectSallary, it.hiringStatus, it.replyMsg, it.repliedAt)
                }

                (binding.rvShowWaitingProject.adapter as ShowWaitingProjectAdapter).addList(list)

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }

        }
    }
}