package com.sizdev.arkhirefortalent.homepage.project.allproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityShowAllProjectBinding
import com.sizdev.arkhirefortalent.networking.ApiClient
import kotlinx.coroutines.*

class ShowAllProjectActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShowAllProjectBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ShowAllProjectApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_all_project)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient()!!.create(ShowAllProjectApiService::class.java)

        binding.rvShowAllProject.adapter = ShowAllProjectAdapter()
        binding.rvShowAllProject.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        showAllProject()
    }

    private fun showAllProject() {
        coroutineScope.launch {
            Log.d("Arkhire Talent", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("Arkhire Talent", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getAllProjectResponse()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ShowAllProjectResponse) {
                Log.d("Arkhire Talent", result.toString())
                val list = result.data?.map{
                    ShowAllProjectModel(it.offeringID, it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.msgForTalent, it.projectSallary, it.hiringStatus, it.replyMsg, it.repliedAt)
                }

                (binding.rvShowAllProject.adapter as ShowAllProjectAdapter).addList(list)
            }

        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}