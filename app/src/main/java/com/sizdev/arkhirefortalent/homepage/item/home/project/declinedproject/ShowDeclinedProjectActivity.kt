package com.sizdev.arkhirefortalent.homepage.item.home.project.declinedproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityShowDeclinedProjectBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*

class ShowDeclinedProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowDeclinedProjectBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_declined_project)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ArkhireApiClient.getApiClient(this)!!.create(ArkhireApiService::class.java)

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        setSupportActionBar(binding.tbShowDeclinedProject)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.rvShowDeclinedProject.adapter = ShowDeclinedProjectAdapter()
        binding.rvShowDeclinedProject.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.tbShowDeclinedProject.setNavigationOnClickListener {
            finish()
        }

        showDeclinedProject()
    }

    private fun showDeclinedProject() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getDeclinedProjectResponse()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ShowDeclinedProjectResponse) {
                val list = result.data?.map{
                    ShowDeclinedProjectModel(it.offeringID, it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.projectSallary, it.hiringStatus, it.replyMsg, it.repliedAt)
                }

                (binding.rvShowDeclinedProject.adapter as ShowDeclinedProjectAdapter).addList(list)

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }

        }
    }
}