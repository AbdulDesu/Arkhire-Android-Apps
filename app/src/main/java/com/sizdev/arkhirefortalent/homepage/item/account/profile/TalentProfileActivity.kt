package com.sizdev.arkhirefortalent.homepage.item.account.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityTalentProfileBinding
import com.sizdev.arkhirefortalent.homepage.HomeActivity
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectAdapter
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectModel
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectResponse
import com.sizdev.arkhirefortalent.networking.ApiClient
import kotlinx.coroutines.*
import java.lang.Runnable

class TalentProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalentProfileBinding
    private lateinit var pagerAdapter: TalentProfileTabAdapter
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: TalentProfileAuthService

    private var doubleBackToExitPressedOnce = false


    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_talent_profile)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(TalentProfileAuthService::class.java)

        //Get Saved Profile
        val sharedPrefData = this.getSharedPreferences("Token", Context.MODE_PRIVATE)
        val talentName = sharedPrefData.getString("accName", null)

        //Enable ViewPager
        pagerAdapter = TalentProfileTabAdapter(supportFragmentManager)
        binding.vpTalentProfile.adapter = pagerAdapter
        binding.tabTalentProfile.setupWithViewPager(binding.vpTalentProfile)

        if (talentName != null) {
            showProfile(talentName)
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.menuButton.setOnClickListener {
            val showMenu = PopupMenu(this, binding.menuButton)
            showMenu.menu.add(Menu.NONE, 0 ,0, "Edit Profile")
            showMenu.menu.add(Menu.NONE, 1 ,1, "Preview Profile")
            showMenu.show()

            showMenu.setOnMenuItemClickListener { menuItem ->
                val id = menuItem.itemId

                when (id) {
                    0 -> {startActivity(Intent(this, EditProfileActivity::class.java))}
                    1 -> {startActivity(Intent(this, HomeActivity::class.java))}
                }
                false
            }
        }

    }

    private fun showProfile(talentName: String) {
        coroutineScope.launch {
            Log.d("Arkhire Talent", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("Arkhire Talent", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getTalentProfileByNameResponse(talentName)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is TalentProfileResponse) {
                Log.d("Arkhire Talent", result.toString())
                binding.tvProfileTalentName.text = result.data.toString()
                Log.d("test", result.data.toString())
                /*val talentEmail = result.data
                val talentPhone = result.data?.accountPhone
                binding.tvProfileTalentTitle.text = result.data?.talentTitle
                binding.tvProfileTalentDesc.text = result.data?.talentDesc
                binding.tvProfileTalentLocation.text = result.data?.talentCity
                val talentGithub = result.data?.talentGithub
                binding.tvTitleProfileTalentSkill.text = result.data?.talentSkill1
                binding.tvTitleProfileTalentSkil2.text = result.data?.talentSkill2
                binding.tvTitleProfileTalentSkil3.text = result.data?.talentSkill3
                binding.tvTitleProfileTalentSkill4.text = result.data?.talentSkill4
                binding.tvTitleProfileTalentSkill5.text = result.data?.talentSkill5

                binding.ivTalentEmail.setOnClickListener {
                    Toast.makeText(this@TalentProfileActivity, "Your Email is: $talentEmail", Toast.LENGTH_SHORT).show()
                }

                binding.ivTalentPhone.setOnClickListener{
                    Toast.makeText(this@TalentProfileActivity, "Your Phone Number is: $talentPhone", Toast.LENGTH_SHORT).show()
                }

                binding.ivTalentGithub.setOnClickListener {
                    Toast.makeText(this@TalentProfileActivity, "Your Github is: https://github.com/$talentGithub", Toast.LENGTH_SHORT).show()
                }*/
            }

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