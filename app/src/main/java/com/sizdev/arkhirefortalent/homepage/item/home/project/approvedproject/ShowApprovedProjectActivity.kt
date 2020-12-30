package com.sizdev.arkhirefortalent.homepage.item.home.project.approvedproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityShowApprovedProjectBinding
import com.sizdev.arkhirefortalent.homepage.HomeActivity
import com.sizdev.arkhirefortalent.networking.ApiClient
import kotlinx.coroutines.*

class ShowApprovedProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowApprovedProjectBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ShowApprovedProjectApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_approved_project)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ShowApprovedProjectApiService::class.java)

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
            Log.d("Arkhire Talent", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("Arkhire Talent", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getApprovedProjectResponse()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ShowApprovedProjectResponse) {
                Log.d("Arkhire Talent", result.toString())
                val list = result.data?.map{
                    ShowApprovedProjectModel(it.offeringID, it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.msgForTalent, it.projectSallary, it.hiringStatus, it.replyMsg, it.repliedAt)
                }

                (binding.rvShowApprovedProject.adapter as ShowApprovedProjectAdapter).addList(list)
            }

        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}