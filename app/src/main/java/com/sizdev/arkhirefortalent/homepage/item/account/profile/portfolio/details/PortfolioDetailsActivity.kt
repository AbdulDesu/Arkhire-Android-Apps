package com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.details

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityPortfolioDetailsBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioAdapter
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioModel
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioResponse
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_portfolio_details.*
import kotlinx.android.synthetic.main.alert_delete_confirmation.view.*
import kotlinx.coroutines.*
import java.lang.Runnable

class PortfolioDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPortfolioDetailsBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_portfolio_details)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ArkhireApiClient.getApiClient(this)!!.create(ArkhireApiService::class.java)

        // Get ID Portfolio
        val portfolioID = intent.getStringExtra("portfolioID")

        // Set FAB
        binding.backButton.setOnClickListener {
            finish()
        }

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        // Data Refresh Management
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                showPortfolioByID(portfolioID!!)
                mainHandler.postDelayed(this, 3000)
            }
        })

        // Delete Action
        binding.tvPortfolioDelete.setOnClickListener {
            portfolioDeleteConfirmation(portfolioID!!)
            dialog.show()
        }

    }


    private fun showPortfolioByID(portfolioID: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getPortfolioByID(portfolioID)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is PortfolioResponse) {
                binding.tvPortfolioTitle.text = result.data[0].portfolioTitle
                binding.tvPortfolioDesc.text = result.data[0].portfolioDesc
                binding.tvPortfolioRepo.text = result.data[0].portfolioRepository
                Picasso.get()
                    .load("http://54.82.81.23:911/image/${result.data[0].portfolioImage}")
                    .resize(512, 512)
                    .centerCrop()
                    .into(binding.ivPortfolioImage)

                if(result.data[0].portfolioRepository == null || result.data[0].portfolioRepository == ""){
                   binding.tvTitlePortfolioRepo.visibility = View.GONE
                   binding.tvPortfolioRepo.visibility = View.GONE
                }

                // End Of Loading
                binding.loadingScreen.visibility = View.GONE
            }
        }
    }

    private fun portfolioDeleteConfirmation(portfolioID: String) {
        val view: View = layoutInflater.inflate(R.layout.alert_delete_confirmation, null)

        dialog = this.let {
            AlertDialog.Builder(it)
                .setView(view)
                .setCancelable(false)
                .create()
        }!!

        view.bt_yesDelete.setOnClickListener {
            deletePortfolio(portfolioID)
            dialog.dismiss()
        }

        view.bt_noDelete.setOnClickListener {
            dialog.cancel()
        }
    }

    private fun  deletePortfolio(portfolioID: String){
        coroutineScope.launch {

            val result = withContext(Dispatchers.IO) {
                try {
                    service.deletePortfolio(portfolioID)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is PortfolioResponse) {
                Toast.makeText(
                    this@PortfolioDetailsActivity,
                    "Portfolio Deleted Successfully",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}