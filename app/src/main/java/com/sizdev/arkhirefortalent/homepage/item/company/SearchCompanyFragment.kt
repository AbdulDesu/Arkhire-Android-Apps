package com.sizdev.arkhirefortalent.homepage.item.company

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_company, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ArkhireApiClient.getApiClient(it) }!!.create(ArkhireApiService::class.java)

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
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getAllCompany()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is SearchCompanyResponse) {

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