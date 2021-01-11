package com.sizdev.arkhirefortalent.homepage.item.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhireforcompany.homepage.item.explore.ExploreModel
import com.sizdev.arkhireforcompany.homepage.item.explore.ExploreResponse
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentExploreBinding
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectAdapter
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectModel
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectResponse
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*
import java.lang.Runnable


class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ArkhireApiClient.getApiClient(it) }!!.create(ArkhireApiService::class.java)

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        // Set Up Recycler View
        binding.rvExplore.adapter = ExploreAdapter()
        binding.rvExplore.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        // Data Refresh Management
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                exploreProject()
                mainHandler.postDelayed(this, 5000)
            }
        })

        // Set Search View
        binding.tbExplorer.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.tbExplorer.clearFocus()
                if (query != null) {
                    binding.rvExplore.visibility = View.VISIBLE
                    binding.lnNotFound.visibility = View.GONE
                    mainHandler.removeCallbacksAndMessages(null)
                    searchProject(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    mainHandler.removeCallbacksAndMessages(null)
                    searchProject(newText)
                }
                return false
            }
        })

        binding.tbExplorer.setOnCloseListener {
            binding.tbExplorer.clearFocus()
            exploreProject()
            false
        }


        return binding.root
    }

    private fun exploreProject() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.startExplore()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ExploreResponse) {
                val list = result.data?.map{
                    ExploreModel(it.projectTitle, it.projectDesc, it.projectSallary, it.projectOwner, it.projectOwnerName, it.projectOwnerImage, it.postedAt)}

                (binding.rvExplore.adapter as ExploreAdapter).addList(list)

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun searchProject(projectTitle: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.searchExplore(projectTitle)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ExploreResponse) {
                val list = result.data?.map{
                    ExploreModel(it.projectTitle, it.projectDesc, it.projectSallary, it.projectOwner, it.projectOwnerName, it.projectOwnerImage, it.postedAt)}

                (binding.rvExplore.adapter as ExploreAdapter).addList(list)

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }

            else {
                binding.rvExplore.visibility = View.GONE
                binding.lnNotFound.visibility = View.VISIBLE
                binding.tvQueryNotfound.text = "Search Result of $projectTitle is not found"
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }


}