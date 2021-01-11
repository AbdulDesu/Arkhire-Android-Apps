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
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.lang.Runnable

class TalentProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalentProfileBinding
    private lateinit var pagerAdapter: TalentProfileTabAdapter
    private var doubleBackToExitPressedOnce = false


    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_talent_profile)

        // Get Saved Data
        val talentID = intent.getStringExtra("talentID")
        val accountID = intent.getStringExtra("accountID")
        val talentName = intent.getStringExtra("talentName")
        val talentTitle = intent.getStringExtra("talentTitle")
        val talentTime = intent.getStringExtra("talentTime")
        val talentLocation = intent.getStringExtra("talentLocation")
        val talentDesc = intent.getStringExtra("talentDesc")
        val talentEmail = intent.getStringExtra("talentEmail")
        val talentPhone = intent.getStringExtra("talentPhone")
        val talentImage = intent.getStringExtra("talentImage")
        val talentGithub = intent.getStringExtra("talentGithub")
        val talentSkill1 = intent.getStringExtra("talentSkill1")
        val talentSkill2 = intent.getStringExtra("talentSkill2")
        val talentSkill3 = intent.getStringExtra("talentSkill3")
        val talentSkill4 = intent.getStringExtra("talentSkill4")
        val talentSkill5 = intent.getStringExtra("talentSkill5")

        // Set Saved Data
        binding.tvProfileTalentName.text = talentName
        binding.tvProfileTalentTitle.text = talentTitle
        binding.tvProfileTalentDesc.text = talentDesc
        binding.tvProfileTalentLocation.text = talentLocation

        //Set Talent Images
        when (talentImage) {
            "null" -> binding.ivTalentProfileImage.setImageResource(R.drawable.ic_empty_image)
            else -> {
                Picasso.get()
                        .load("http://54.82.81.23:911/image/$talentImage")
                        .resize(512, 512)
                        .centerCrop()
                        .into(binding.ivTalentProfileImage)
            }
        }

        // Set Cover Image
        if (talentTime == "Freelance") {
            binding.ivTalentProfileCover.setImageResource(R.drawable.ic_freelancer)
        } else {
            binding.ivTalentProfileCover.setImageResource(R.drawable.ic_fulltimework)
        }

        //Validate Skill Null or not
        if (talentSkill1 != "null") {
            binding.tvTitleProfileTalentSkill1.text = talentSkill1
        } else {
            binding.tvTitleProfileTalentSkill1.visibility = View.INVISIBLE
        }

        if (talentSkill2 != "null") {
            binding.tvTitleProfileTalentSkill2.text = talentSkill2
        } else {
            binding.tvTitleProfileTalentSkill2.visibility = View.INVISIBLE
        }

        if (talentSkill3 != "null") {
            binding.tvTitleProfileTalentSkill3.text = talentSkill3
        } else {
            binding.tvTitleProfileTalentSkill3.visibility = View.INVISIBLE
        }

        if (talentSkill4 != "null") {
            binding.tvTitleProfileTalentSkill4.text = talentSkill4
        } else {
            binding.tvTitleProfileTalentSkill4.visibility = View.INVISIBLE
        }

        if (talentSkill5 != "null") {
            binding.tvTitleProfileTalentSkill5.text = talentSkill5
        } else {
            binding.tvTitleProfileTalentSkill5.visibility = View.INVISIBLE
        }


        binding.ivTalentEmail.setOnClickListener {
            Toast.makeText(this@TalentProfileActivity, "Your Email is: $talentEmail", Toast.LENGTH_SHORT).show()
        }

        binding.ivTalentPhone.setOnClickListener {
            Toast.makeText(this@TalentProfileActivity, "Your Phone Number is: $talentPhone", Toast.LENGTH_SHORT).show()
        }

        binding.ivTalentGithub.setOnClickListener {
            Toast.makeText(this@TalentProfileActivity, "Your Github is: https://github.com/$talentGithub", Toast.LENGTH_SHORT).show()
        }

        binding.menuButton.setOnClickListener {
            val showMenu = PopupMenu(this@TalentProfileActivity, binding.menuButton)
            showMenu.menu.add(Menu.NONE, 0, 0, "Edit Profile")
            showMenu.menu.add(Menu.NONE, 1, 1, "Preview Profile")
            showMenu.show()

            showMenu.setOnMenuItemClickListener { menuItem ->
                val id = menuItem.itemId

                when (id) {
                    0 -> {
                        val intent = Intent(this@TalentProfileActivity, EditProfileActivity::class.java)
                        intent.putExtra("talentID", talentID)
                        intent.putExtra("talentLocation", talentLocation)
                        intent.putExtra("talentTitle", talentTitle)
                        intent.putExtra("talentDesc", talentDesc)
                        intent.putExtra("talentImage", talentImage)
                        startActivity(intent)
                    }
                    1 -> {
                        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
                    }
                }
                false
            }
        }

        // End Of Loading
        binding.loadingScreen.visibility = View.GONE


        //Enable ViewPager
        pagerAdapter = TalentProfileTabAdapter(supportFragmentManager)
        binding.vpTalentProfile.adapter = pagerAdapter
        binding.tabTalentProfile.setupWithViewPager(binding.vpTalentProfile)


        binding.backButton.setOnClickListener {
            finish()
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