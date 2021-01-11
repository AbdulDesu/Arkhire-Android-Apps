package com.sizdev.arkhirefortalent.homepage.item.company

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentSearchCompanyBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*


class SearchCompanyFragment : Fragment() {

    private lateinit var binding: FragmentSearchCompanyBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_company,
            container,
            false
        )
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ArkhireApiClient.getApiClient(it) }!!.create(ArkhireApiService::class.java)

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        //Show Company List Data
        binding.rvCompany.adapter = SearchCompanyAdapter()
        binding.rvCompany.layoutManager = GridLayoutManager(
            activity,
            2,
            GridLayoutManager.VERTICAL,
            false
        )

        // Data Refresh Management
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                showAllCompany()
                mainHandler.postDelayed(this, 5000)
            }
        })

        // Search Management

        binding.svSearchCompany.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svSearchCompany.clearFocus()
                if (query != null) {
                    mainHandler.removeCallbacksAndMessages(null)
                    searchCompany(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    mainHandler.removeCallbacksAndMessages(null)
                    searchCompany(newText)
                }
                return false
            }
        })

        binding.svSearchCompany.setOnCloseListener {
            binding.svSearchCompany.clearFocus()
            showAllCompany()
            false
        }



        return binding.root
    }

    private fun showAllCompany() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getAllCompany()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is SearchCompanyResponse) {

                val list = result.data?.map{
                    SearchCompanyModel(
                        it.companyID,
                        it.accountID,
                        it.companyName,
                        it.companyPosition,
                        it.companyLocation,
                        it.companyLatitude,
                        it.companyLongitude,
                        it.companyType,
                        it.companyDesc,
                        it.companyLinkedin,
                        it.companyInstagram,
                        it.companyFacebook,
                        it.companyImage,
                        it.updatedAt
                    )
                }

                (binding.rvCompany.adapter as SearchCompanyAdapter).addList(list)

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun searchCompany(companyName: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.searchCompany(companyName)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is SearchCompanyResponse) {

                val list = result.data?.map{
                    SearchCompanyModel(
                        it.companyID,
                        it.accountID,
                        it.companyName,
                        it.companyPosition,
                        it.companyLocation,
                        it.companyLatitude,
                        it.companyLongitude,
                        it.companyType,
                        it.companyDesc,
                        it.companyLinkedin,
                        it.companyInstagram,
                        it.companyFacebook,
                        it.companyImage,
                        it.updatedAt
                    )
                }

                (binding.rvCompany.adapter as SearchCompanyAdapter).addList(list)

                binding.lnNotFound.visibility = View.GONE
                binding.rvCompany.visibility = View.VISIBLE
            }

            else {
                binding.rvCompany.visibility = View.GONE
                binding.lnNotFound.visibility = View.VISIBLE
                binding.tvQueryNotfound.text = "Search Result of $companyName is not found"
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}