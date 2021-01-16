package com.sizdev.arkhirefortalent.homepage.item.explore

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhireforcompany.homepage.item.explore.ExploreResponse
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.databinding.FragmentExploreBinding
import com.sizdev.arkhirefortalent.homepage.item.home.HomePresenter
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.android.synthetic.main.alert_session_expired.view.*
import kotlinx.coroutines.*
import java.lang.Runnable


class ExploreFragment : Fragment(), ExploreContract.View {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var dialog: AlertDialog
    private lateinit var handler: Handler

    private var presenter : ExplorePresenter? =null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        val service = activity?.let { ArkhireApiClient.getApiClient(it)?.create(ArkhireApiService::class.java) }
        presenter = ExplorePresenter(coroutineScope, service)

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
                presenter?.exploreProject()
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
                    presenter?.searchProject(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    mainHandler.removeCallbacksAndMessages(null)
                    presenter?.searchProject(newText)
                }
                return false
            }
        })

        binding.tbExplorer.setOnCloseListener {
            binding.tbExplorer.clearFocus()
            presenter?.exploreProject()
            false
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
    }


    override fun showExploreList(list: List<ExploreModel>) {
        (binding.rvExplore.adapter as ExploreAdapter).addList(list)
    }

    override fun setError(error: String) {
        if (error == "Session Expired !"){
            sessionExpiredAlert()
            dialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun hideProgressBar() {
        binding.loadingScreen.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun showNotFound() {
        binding.rvExplore.visibility = View.GONE
        binding.lnNotFound.visibility = View.VISIBLE
        binding.tvQueryNotfound.text = "No Project Found With That Name"
    }

    private fun sessionExpiredAlert() {
        handler.removeCallbacksAndMessages(null)
        val view: View = layoutInflater.inflate(R.layout.alert_session_expired, null)

        dialog = activity?.let {
            AlertDialog.Builder(it)
                    .setView(view)
                    .setCancelable(false)
                    .create()
        }!!

        view.bt_okRelog.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(activity, LoginActivity::class.java)
            val sharedPref = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("accID", null)
            editor.apply()
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        presenter = null
    }
}