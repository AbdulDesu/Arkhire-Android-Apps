package com.sizdev.arkhirefortalent.homepage.item.company

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.databinding.FragmentSearchCompanyBinding
import com.sizdev.arkhirefortalent.homepage.item.home.HomePresenter
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.android.synthetic.main.alert_session_expired.view.*
import kotlinx.coroutines.*


class SearchCompanyFragment : Fragment(), SearchCompanyContract.View {

    private lateinit var binding: FragmentSearchCompanyBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService
    private lateinit var handler: Handler
    private lateinit var dialog: AlertDialog

    private var presenter: SearchCompanyPresenter? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_company, container, false)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        val service = activity?.let { ArkhireApiClient.getApiClient(it)?.create(ArkhireApiService::class.java) }
        presenter = SearchCompanyPresenter(coroutineScope, service)

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
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                presenter?.getCompanyList()
                handler.postDelayed(this, 5000)
            }
        })

        // Search Management
        binding.svSearchCompany.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svSearchCompany.clearFocus()
                if (query != null) {
                    handler.removeCallbacksAndMessages(null)
                    binding.lnNotFound.visibility = View.GONE
                    binding.rvCompany.visibility = View.VISIBLE
                    presenter?.searchCompany(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    handler.removeCallbacksAndMessages(null)
                    binding.lnNotFound.visibility = View.GONE
                    binding.rvCompany.visibility = View.VISIBLE
                    presenter?.searchCompany(newText)
                }
                return false
            }
        })

        binding.svSearchCompany.setOnCloseListener {
            binding.svSearchCompany.clearFocus()
            presenter?.getCompanyList()
            false
        }

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
    }

    override fun addCompanyList(list: List<SearchCompanyModel>) {
        (binding.rvCompany.adapter as SearchCompanyAdapter).addList(list)
    }

    override fun setError(error: String) {
        if (error == "Session Expired !"){
            sessionExpiredAlert()
            dialog.show()
        }
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

    override fun hideProgressBar() {
        binding.loadingScreen.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun showNotFound() {
        binding.rvCompany.visibility = View.GONE
        binding.lnNotFound.visibility = View.VISIBLE
        binding.tvQueryNotfound.text = "Company With That Name is Not Found !"
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