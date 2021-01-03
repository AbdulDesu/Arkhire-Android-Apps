package com.sizdev.arkhirefortalent.homepage.item.account.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityTalentProfileBinding
import com.sizdev.arkhirefortalent.homepage.HomeActivity
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditProfileActivity
import com.sizdev.arkhirefortalent.networking.ApiClient
import com.squareup.picasso.Picasso
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
                binding.tvProfileTalentName.text = result.data[0].accountName
                val talentEmail = result.data[0].accountEmail
                val talentPhone = result.data[0].accountPhone
                binding.tvProfileTalentTitle.text = result.data[0].talentTitle
                binding.tvProfileTalentDesc.text = result.data[0].talentDesc
                val talentImage = result.data[0].talentImage
                binding.tvProfileTalentLocation.text = result.data[0].talentCity
                val talentGithub = result.data[0].talentGithub

                //Validate Skill Null or not
                if(result.data[0].talentSkill1 != null){binding.tvTitleProfileTalentSkill1.text = result.data[0].talentSkill1}
                else{binding.tvTitleProfileTalentSkill1.visibility = View.INVISIBLE}

                if(result.data[0].talentSkill2 != null){binding.tvTitleProfileTalentSkill2.text = result.data[0].talentSkill2}
                else{binding.tvTitleProfileTalentSkill2.visibility = View.INVISIBLE}

                if(result.data[0].talentSkill3 != null){binding.tvTitleProfileTalentSkil3.text = result.data[0].talentSkill3}
                else{binding.tvTitleProfileTalentSkil3.visibility = View.INVISIBLE}

                if(result.data[0].talentSkill4 != null){binding.tvTitleProfileTalentSkill4.text = result.data[0].talentSkill4}
                else{binding.tvTitleProfileTalentSkill4.visibility = View.INVISIBLE}

                if(result.data[0].talentSkill5 != null){binding.tvTitleProfileTalentSkill5.text = result.data[0].talentSkill5}
                else{binding.tvTitleProfileTalentSkill5.visibility = View.INVISIBLE}

                //Set Talent Images
                Picasso.get()
                        .load("http://54.82.81.23:911/image/$talentImage")
                        .resize(512, 512)
                        .centerCrop()
                        .into(binding.ivTalentProfileImage)

                binding.ivTalentEmail.setOnClickListener {
                    Toast.makeText(this@TalentProfileActivity, "Your Email is: $talentEmail", Toast.LENGTH_SHORT).show()
                }

                binding.ivTalentPhone.setOnClickListener{
                    Toast.makeText(this@TalentProfileActivity, "Your Phone Number is: $talentPhone", Toast.LENGTH_SHORT).show()
                }

                binding.ivTalentGithub.setOnClickListener {
                    Toast.makeText(this@TalentProfileActivity, "Your Github is: https://github.com/$talentGithub", Toast.LENGTH_SHORT).show()
                }

                binding.menuButton.setOnClickListener {
                    val showMenu = PopupMenu(this@TalentProfileActivity, binding.menuButton)
                    showMenu.menu.add(Menu.NONE, 0 ,0, "Edit Profile")
                    showMenu.menu.add(Menu.NONE, 1 ,1, "Preview Profile")
                    showMenu.show()

                    showMenu.setOnMenuItemClickListener { menuItem ->
                        val id = menuItem.itemId

                        when (id) {
                            0 -> {
                                val intent = Intent(this@TalentProfileActivity, EditProfileActivity::class.java)
                                intent.putExtra("talentID", result.data[0].talentID)
                                intent.putExtra("talentLocation", result.data[0].talentID)
                                intent.putExtra("talentTitle", result.data[0].talentTitle)
                                intent.putExtra("talentDesc", result.data[0].talentDesc)
                                intent.putExtra("talentImage", result.data[0].talentImage)
                                startActivity(intent)
                            }
                            1 -> {
                                startActivity(Intent(this@TalentProfileActivity, HomeActivity::class.java))
                            }
                        }
                        false
                    }
                }
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

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}