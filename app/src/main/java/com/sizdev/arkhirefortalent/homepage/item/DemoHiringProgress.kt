package com.sizdev.arkhirefortalent.homepage.item

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayout
import com.sizdev.arkhirefortalent.R
import kotlinx.android.synthetic.main.activity_demo_hiring_progress.*
import kotlinx.android.synthetic.main.activity_talent_profile.tv_profileTalentLocation
import kotlinx.android.synthetic.main.activity_talent_profile.tv_profileTalentName
import kotlinx.android.synthetic.main.activity_talent_profile.tv_profileTalentTitle
import kotlinx.android.synthetic.main.hiring_confirmation.view.*

private lateinit var dialog: AlertDialog

class DemoHiringProgress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_hiring_progress)

        //Get Saved Profile
        val sharedPrefData = this.getSharedPreferences("fullData", Context.MODE_PRIVATE)
        val sharedPrefProfileData = this.getSharedPreferences("profileData", Context.MODE_PRIVATE)
        val savedName = sharedPrefData.getString("fullName", null)
        val talentEmail = sharedPrefData.getString("talentEmail", null)
        val profileTitle = sharedPrefProfileData.getString("talentTitle", null)
        val profileLocation = sharedPrefProfileData.getString("talentLocation", null)
        val profileWorkTime = sharedPrefProfileData.getString("talentWorkTime", null)
        val profileDesc = sharedPrefProfileData.getString("talentDescription", null)
        val profileGithub = sharedPrefProfileData.getString("talentGithub", null)
        val talentSkill1 = sharedPrefProfileData.getString("talentSkill1", null)
        val talentSkill2 = sharedPrefProfileData.getString("talentSkill2", null)
        val talentSkill3 = sharedPrefProfileData.getString("talentSkill3", null)
        val talentSkill4 = sharedPrefProfileData.getString("talentSkill4", null)
        val talentSkill5 = sharedPrefProfileData.getString("talentSkill5", null)


        //Insert Profile Data
        tv_profileTalentName.text = savedName
        tv_profileTalentEmail.text = talentEmail
        tv_workTime.text = profileWorkTime
        tv_profileTalentTitle.text = profileTitle
        tv_profileTalentLocation.text = profileLocation
        tv_profileTalentDesc.text = profileDesc
        profile_talentGithub.text = profileGithub
        profile_talentSkill1.text = talentSkill1
        profile_talentSkill2.text = talentSkill2
        profile_talentSkill3.text = talentSkill3
        profile_talentSkill4.text = talentSkill4
        profile_talentSkill5.text = talentSkill5

        val tabLayout: TabLayout = profile_talentTab

        tabLayout.addTab(tabLayout.newTab().setText("Portofolio"))
        tabLayout.addTab(tabLayout.newTab().setText("Pengalaman Kerja"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        startAlertConfirmation()
        talentHire.setOnClickListener {
            dialog.show()
        }
    }

    private fun startAlertConfirmation () {
        val view: View = layoutInflater.inflate(R.layout.hiring_confirmation, null)

        dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()

        view.bt_yesHire.setOnClickListener {
            view.et_messageForTalent.setVisibility(View.VISIBLE)
            view.confirmationPrompt.setVisibility(View.INVISIBLE)
            view.bt_sendOffering.setVisibility(View.VISIBLE)
        }

        view.bt_sendOffering.setOnClickListener {
            view.et_messageForTalent.text.toString()
            dialog.dismiss()
        }

        view.bt_dontHire.setOnClickListener {
            dialog.cancel()
        }

    }
}
