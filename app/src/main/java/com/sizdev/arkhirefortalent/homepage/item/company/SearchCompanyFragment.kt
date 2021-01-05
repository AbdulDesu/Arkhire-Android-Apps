package com.sizdev.arkhirefortalent.homepage.item.company

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentSearchCompanyBinding
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectAdapter
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectModel
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectResponse
import com.sizdev.arkhirefortalent.networking.ApiClient
import kotlinx.coroutines.*

class SearchCompanyFragment : Fragment() {

    private lateinit var binding: FragmentSearchCompanyBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: SearchCompanyApiService
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_company, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ApiClient.getApiClient(it) }!!.create(SearchCompanyApiService::class.java)

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        //Show Company List Data
        binding.rvCompany.adapter = SearchCompanyAdapter()
        binding.rvCompany.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        showAllCompany()


        return binding.root
    }

    private fun showAllCompany() {
        coroutineScope.launch {
            Log.d("Arkhire Talent", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("Arkhire Talent", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getAllCompany()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is SearchCompanyResponse) {
                Log.d("Arkhire Talent", result.toString())
                val list = result.data?.map{
                    SearchCompanyModel(it.companyID, it.accountID, it.companyName, it.companyPosition, it.companyLatitude, it.companyLongitude, it.companyType, it.companyDesc, it.companyLinkedin, it.companyInstagram, it.companyFacebook, it.companyImage, it.updatedAt)
                }

                (binding.rvCompany.adapter as SearchCompanyAdapter).addList(list)

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