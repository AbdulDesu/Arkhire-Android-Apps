package com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityWorkExperienceDetailBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience.WorkExperienceResponse
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.android.synthetic.main.alert_delete_confirmation.view.*
import kotlinx.coroutines.*

class WorkExperienceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkExperienceDetailBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_experience_detail)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ArkhireApiClient.getApiClient(this)!!.create(ArkhireApiService::class.java)

        // Get ID Work Experience
        val experienceID = intent.getStringExtra("experienceID")
        val experienceTitle = intent.getStringExtra("experienceTitle")
        val experienceSource = intent.getStringExtra("experienceSource")
        val experienceStart = intent.getStringExtra("experienceStart")
        val experienceEnd = intent.getStringExtra("experienceEnd")
        val experienceDesc = intent.getStringExtra("experienceDesc")

        // Set Data
        binding.tvExperienceTitle.text = experienceTitle
        binding.tvExperienceFrom.text = experienceSource
        binding.tvExperienceStart.text = experienceStart
        binding.tvExperienceEnd.text = experienceEnd
        binding.tvExperienceDesc.text = experienceDesc

        binding.tvExperienceDelete.setOnClickListener {
            experienceDeleteConfirmation(experienceID!!)
            dialog.show()
        }
    }

    private fun experienceDeleteConfirmation(experienceID: String) {

        val view: View = layoutInflater.inflate(R.layout.alert_delete_confirmation, null)

        dialog = this.let {
            AlertDialog.Builder(it)
                .setView(view)
                .setCancelable(false)
                .create()
        }!!

        view.bt_yesDelete.setOnClickListener {
            deleteExperience(experienceID)
            dialog.dismiss()
        }

        view.bt_noDelete.setOnClickListener {
            dialog.cancel()
        }

    }

    private fun deleteExperience(experienceID: String) {
        coroutineScope.launch {

            val result = withContext(Dispatchers.IO) {
                try {
                    service.deleteWorkExperience(experienceID)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is WorkExperienceResponse) {
                Toast.makeText(
                    this@WorkExperienceDetailActivity,
                    "Experience Deleted Successfully",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }
}