package com.sizdev.arkhirefortalent.homepage.item.explore.contributor

import android.annotation.SuppressLint
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.databinding.ActivityContributorBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditProfileViewModel
import com.sizdev.arkhirefortalent.homepage.item.explore.ExploreAdapter
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_session_expired.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import java.text.NumberFormat
import java.util.*

class ContributorActivity : AppCompatActivity(), ContributorContract.View {

    private lateinit var binding: ActivityContributorBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var dialog: AlertDialog
    private lateinit var handler: Handler

    private var presenter: ContributorPresenter? = null
    private var projectTag: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contributor)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        val service = ArkhireApiClient.getApiClient(this)?.create(ArkhireApiService::class.java)
        presenter = ContributorPresenter(coroutineScope, service)

        // Get Data
        val projectID = intent.getStringExtra("projectID")
        val projectName = intent.getStringExtra("projectName")
        val projectOwner = intent.getStringExtra("companyName")
        val projectSalary = intent.getStringExtra("projectSalary")
        val projectDuration = intent.getStringExtra("projectDuration")
        val projectDesc = intent.getStringExtra("projectDesc")
        val projectImage = intent.getStringExtra("projectImage")
        val companyImage = intent.getStringExtra("companyImage")

        // Currency Converter
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")

        // Set Data
        projectTag = projectID!!
        binding.tvProjectTitle.text = projectName
        binding.tvCompanyName.text = projectOwner
        binding.tvCompanyProjectDuration.text = "Deadline: $projectDuration"
        binding.tvCompanyProjectSalary.text = format.format(projectSalary!!.toDouble())
        binding.tvProjectDesc.text = projectDesc

        // Set Image
        when (projectImage){
            "null" -> binding.ivProjectImage.setImageResource(R.drawable.ic_project_bg)
            else -> {
                Picasso.get()
                        .load("http://54.82.81.23:911/image/$projectImage")
                        .resize(1280, 500)
                        .centerCrop()
                        .into(binding.ivProjectImage)

                Picasso.get()
                        .load("http://54.82.81.23:911/image/$companyImage")
                        .resize(512, 512)
                        .centerCrop()
                        .into(binding.ivCompanyImage)
            }
        }


        // Set Up Recycler View
        binding.contributorList.adapter = ContributorAdapter()
        binding.contributorList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // Enable Navigation Button
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.likeButton.setOnClickListener {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
        }


    }

    override fun addListContributor(list: List<ContributorModel>) {
        (binding.contributorList.adapter as ContributorAdapter).addList(list)
    }

    override fun setError(error: String) {
        if (error == "Session Expired !"){
            sessionExpiredAlert()
            dialog.show()
        }
    }

    override fun hideProgressBar() {
        binding.loadingScreen.visibility = View.GONE
    }

    override fun showNotFound() {
        binding.emptyContributor.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)

        // Data Refresh Management
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                presenter?.showContributorApi(projectTag!!)
                mainHandler.postDelayed(this, 50000)
            }
        })

        binding.loadingScreen.visibility = View.VISIBLE
    }

    private fun sessionExpiredAlert() {
        handler.removeCallbacksAndMessages(null)
        val view: View = layoutInflater.inflate(R.layout.alert_session_expired, null)

        dialog = this.let {
            AlertDialog.Builder(it)
                    .setView(view)
                    .setCancelable(false)
                    .create()
        }!!

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
        presenter?.unbind()
        super.onStop()
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        presenter = null
        super.onDestroy()
    }
}