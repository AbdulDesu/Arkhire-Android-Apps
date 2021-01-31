package com.sizdev.arkhirefortalent.homepage.item.account.profile.previewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentPortofolioBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioAdapter
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioModel
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.create.CreatePortfolioActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*
import java.lang.Runnable


class PortfolioGuestFragment : Fragment() {

    private lateinit var binding: FragmentPortofolioBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService
    private lateinit var mainHandler: Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_portofolio, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ArkhireApiClient.getApiClient(it) }!!.create(ArkhireApiService::class.java)

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        // Get guest accountID
        val accountID = activity?.intent?.getStringExtra("guestAccountID")

        // Check View Code
        checkViewCode()

        // Data Refresh Management
        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                if (accountID != null) {
                    showPortfolio(accountID)
                }
                mainHandler.postDelayed(this, 5000)
            }
        })

        // Set Recycler View
        binding.rvPortfolio.adapter = PreviewPortfolioAdapter()
        binding.rvPortfolio.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        // Upload Portfolio
        binding.btAddPortfolio.setOnClickListener {
            val intent = Intent(activity, CreatePortfolioActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun checkViewCode() {
        val viewCode = activity?.intent?.getStringExtra("previewCode")

        if (viewCode == "owner" || viewCode == "guest"){
            binding.btAddPortfolio.visibility = View.GONE
        }
    }

    private fun showPortfolio(accountID: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getPortfolio(accountID)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is PortfolioResponse) {
                val list = result.data?.map{
                    PortfolioModel(it.portfolioID, it.portfolioOwner, it.portfolioTitle, it.portfolioDesc, it.portfolioRepository, it.portfolioImage)
                }

                (binding.rvPortfolio.adapter as PreviewPortfolioAdapter).addList(list)

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }

            else {
                // End Of Loading
                binding.loadingScreen.visibility = View.GONE

                // Show Empty Data Alert
                binding.rvPortfolio.visibility = View.GONE
                binding.emptyData.visibility = View.VISIBLE

            }
        }
    }

    override fun onDestroy() {
        mainHandler.removeCallbacksAndMessages(null)
        coroutineScope.cancel()
        super.onDestroy()
    }

}