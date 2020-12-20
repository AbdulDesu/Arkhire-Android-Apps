package com.sizdev.arkhirefortalent.homepage.profile

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityTalentProfileBinding
import com.sizdev.arkhirefortalent.homepage.webviewer.ArkhireWebViewerActivity

class TalentProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalentProfileBinding
    private lateinit var pagerAdapter: TalentProfileTabAdapter
    private var doubleBackToExitPressedOnce = false

    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_talent_profile)

        //Get Saved Profile
        val sharedPrefData = this.getSharedPreferences("fullData", Context.MODE_PRIVATE)
        val savedName = sharedPrefData.getString("fullName", null)
        val talentEmail = sharedPrefData.getString("talentEmail", null)
        val talentPhone = sharedPrefData.getString("talentPhone", null)

        val lorem: String = getString(R.string.lorem_ipsum)

        pagerAdapter = TalentProfileTabAdapter(supportFragmentManager)
        binding.vpTalentProfile.adapter = pagerAdapter
        binding.tabTalentProfile.setupWithViewPager(binding.vpTalentProfile)


        //Insert Profile Data
        binding.tvProfileTalentName.text = savedName
        binding.tvProfileTalentDesc.text = lorem

        binding.ivTalentPhone.setOnClickListener{
            val call = Intent(ACTION_CALL, Uri.fromParts("tel", talentPhone, null))
            startActivity(call)
        }

        binding.ivTalentEmail.setOnClickListener {
            val sendEmail = Intent(ACTION_SENDTO)
            sendEmail.putExtra(EXTRA_EMAIL, talentEmail)
            sendEmail.putExtra(EXTRA_SUBJECT, "Arkhire Email")
            sendEmail.data = Uri.parse("mailto: $talentEmail")
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(FLAG_FROM_BACKGROUND)
            try {
                startActivity(sendEmail)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Log.d("Email error:", e.toString())
            }
        }

        binding.ivTalentGithub.setOnClickListener {
            val intent = Intent(this, ArkhireWebViewerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tap back again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}