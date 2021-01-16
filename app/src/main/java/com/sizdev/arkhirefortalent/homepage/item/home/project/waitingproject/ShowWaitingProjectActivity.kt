package com.sizdev.arkhirefortalent.homepage.item.home.project.waitingproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.databinding.ActivityShowWaitingProjectBinding
import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectAdapter
import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectContract
import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectModel
import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectPresenter
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.android.synthetic.main.alert_session_expired.view.*
import kotlinx.coroutines.*

class ShowWaitingProjectActivity : AppCompatActivity(), ProjectContract.View {

    private lateinit var binding: ActivityShowWaitingProjectBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var handler: Handler
    private lateinit var dialog: AlertDialog

    private var presenter: ProjectPresenter? =null
    private var accountID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_waiting_project)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        // Set Service
        setService()

        // Get Current Login Data
        getCurrentLoginData()

        // Set Action Bar
        setActionBar()

        // Set Up Recyclerview
        setRecyclerView()

        // Set Up Refresh Manager
        setRefreshManager()

        // Show Progress Bar
        showProgressBar()
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
    }

    override fun addProjectList(list: List<ProjectModel>) {
        (binding.rvShowWaitingProject.adapter as ProjectAdapter).addList(list)

    }

    override fun setRefreshManager() {
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                presenter?.getWaitingProject(accountID!!)
                handler.postDelayed(this, 5000)
            }
        })
    }

    override fun setService() {
        val service = ArkhireApiClient.getApiClient(this)!!.create(ArkhireApiService::class.java)
        presenter = ProjectPresenter(coroutineScope, service)
    }

    override fun setError(error: String) {
        when (error) {
            "Session Expired !" -> {
                showSessionExpired()
            }

            "Project Not Found !" -> {
                binding.loadingScreen.visibility = View.GONE
                binding.notFound.visibility = View.VISIBLE
            }

            else -> {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getCurrentLoginData() {
        val sharedPrefData = this.getSharedPreferences("Token", Context.MODE_PRIVATE)
        accountID = sharedPrefData.getString("accID", null)
    }

    override fun showProgressBar() {
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100
    }

    override fun setRecyclerView() {
        binding.rvShowWaitingProject.adapter = ProjectAdapter()
        binding.rvShowWaitingProject.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun hideProgressBar() {
        binding.loadingScreen.visibility = View.GONE
    }

    override fun setActionBar() {
        setSupportActionBar(binding.tbShowWaitingProject)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.tbShowWaitingProject.setNavigationOnClickListener {
            finish()
        }
    }

    override fun showSessionExpired() {
        val view: View = layoutInflater.inflate(R.layout.alert_session_expired, null)

        dialog = this.let {
            AlertDialog.Builder(it)
                    .setView(view)
                    .setCancelable(false)
                    .create()
        }

        view.bt_okRelog.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, LoginActivity::class.java)
            val sharedPref = this.getSharedPreferences("Token", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("accID", null)
            editor.apply()
            startActivity(intent)
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}