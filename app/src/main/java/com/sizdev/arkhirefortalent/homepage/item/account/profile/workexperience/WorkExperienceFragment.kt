package com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentWorkExperienceBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioAdapter
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioModel
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience.add.AddWorkExperienceActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*
import java.lang.Runnable


class WorkExperienceFragment : Fragment() {

    private lateinit var binding: FragmentWorkExperienceBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService
    private lateinit var mainHandler: Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_work_experience, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ArkhireApiClient.getApiClient(it) }!!.create(ArkhireApiService::class.java)

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        // Check View Code
        checkViewCode()

        // Get Current Logged in Account ID
        val sharedPrefData = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
        val accountID = sharedPrefData.getString("accID", null)


        // Set Up RecyclerView
        binding.rvTalentWorkExperience.adapter = WorkExperienceAdapter()
        binding.rvTalentWorkExperience.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        // Data Refresh Management
        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                if (accountID != null) {
                    showExperiences(accountID)
                }
                mainHandler.postDelayed(this, 5000)
            }
        })

        binding.btAddExperience.setOnClickListener {
            val intent = Intent(activity, AddWorkExperienceActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun checkViewCode() {
        val viewCode = activity?.intent?.getStringExtra("previewCode")

        if (viewCode == "owner" || viewCode == "guest"){
            binding.btAddExperience.visibility = View.GONE
        }
    }

    private fun showExperiences(accountID: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getWorkExperiences(accountID)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is WorkExperienceResponse) {
                val list = result.data?.map{
                WorkExperienceModel(it.experienceID, it.experienceOwner, it.experienceTitle, it.experienceSource, it.experienceStart, it.experienceEnd, it.experienceDesc)}

                (binding.rvTalentWorkExperience.adapter as WorkExperienceAdapter).addList(list)

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
                binding.emptyData.visibility = View.GONE
                binding.rvTalentWorkExperience.visibility = View.VISIBLE
            }

            else {
                // End Of Loading
                binding.loadingScreen.visibility = View.GONE

                // Show Empty Data Alert
                binding.rvTalentWorkExperience.visibility = View.GONE
                binding.emptyData.visibility = View.VISIBLE

            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}