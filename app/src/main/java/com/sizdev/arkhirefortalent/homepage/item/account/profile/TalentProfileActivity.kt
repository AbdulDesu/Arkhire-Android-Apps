package com.sizdev.arkhirefortalent.homepage.item.account.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.databinding.ActivityTalentProfileBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditProfileActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_session_expired.view.*
import kotlinx.coroutines.*

class TalentProfileActivity : AppCompatActivity(), TalentProfileContract.View {

    private lateinit var binding: ActivityTalentProfileBinding
    private lateinit var pagerAdapter: TalentProfileTabAdapter
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var handler: Handler
    private lateinit var dialog: AlertDialog

    private var talentID: String? = null
    private var presenter: TalentProfilePresenter? = null


    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_talent_profile)

        // Set Service
        setService()

        // Get Saved Data
        getSavedData()

        // Show Progress Bar
        showProgressBar()

        // Set Profile Tab
        setProfileTab()

        // Set FAB
        setFloatingButton()

        // Set Data Refresh Manager
        setRefreshManager()

    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    override fun setService() {
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        val service = this.let { ArkhireApiClient.getApiClient(it)?.create(ArkhireApiService::class.java) }
        presenter = TalentProfilePresenter(coroutineScope, service)
    }

    override fun showProgressBar() {
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100
    }

    override fun getSavedData() {
        talentID = intent.getStringExtra("talentID")
    }

    override fun setTalentData(talentID: String?, accountName: String?, accountEmail: String?, accountPhone: String?, talentTitle: String?, talentTime: String?, talentCity: String?, talentDesc: String?, talentImage: String?, talentGithub: String?, talentCv: String?, talentSkill1: String?, talentSkill2: String?, talentSkill3: String?, talentSkill4: String?, talentSkill5: String?) {
        binding.tvProfileTalentName.text = accountName
        binding.tvProfileTalentTitle.text = talentTitle
        binding.tvProfileTalentDesc.text = talentDesc
        binding.tvProfileTalentLocation.text =  talentCity

        Picasso.get()
                .load("http://54.82.81.23:911/image/$talentImage")
                .resize(512, 512)
                .centerCrop()
                .into(binding.ivTalentProfileImage)

        if (talentTime == "Freelance") {
            binding.ivTalentProfileCover.setImageResource(R.drawable.ic_freelancer)
        } else {
            binding.ivTalentProfileCover.setImageResource(R.drawable.ic_fulltimework)
        }

        //Validate Skill Null or not
        if (talentSkill1 != null) {
            binding.tvTitleProfileTalentSkill1.text = talentSkill1
        } else {
            binding.tvTitleProfileTalentSkill1.visibility = View.INVISIBLE
        }

        if (talentSkill2 != null) {
            binding.tvTitleProfileTalentSkill2.text = talentSkill2
        } else {
            binding.tvTitleProfileTalentSkill2.visibility = View.INVISIBLE
        }

        if (talentSkill3 != null) {
            binding.tvTitleProfileTalentSkill3.text = talentSkill3
        } else {
            binding.tvTitleProfileTalentSkill3.visibility = View.INVISIBLE
        }

        if (talentSkill4 != null) {
            binding.tvTitleProfileTalentSkill4.text = talentSkill4
        } else {
            binding.tvTitleProfileTalentSkill4.visibility = View.INVISIBLE
        }

        if (talentSkill5 != null) {
            binding.tvTitleProfileTalentSkill5.text = talentSkill5
        } else {
            binding.tvTitleProfileTalentSkill5.visibility = View.INVISIBLE
        }

        binding.menuButton.setOnClickListener {
            val showMenu = PopupMenu(this@TalentProfileActivity, binding.menuButton)
            showMenu.menu.add(Menu.NONE, 0, 0, "Edit Profile")
            showMenu.menu.add(Menu.NONE, 1, 1, "Preview Profile")
            showMenu.show()

            showMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    0 -> {
                        val intent = Intent(this@TalentProfileActivity, EditProfileActivity::class.java)
                        intent.putExtra("talentID", talentID)
                        intent.putExtra("talentLocation", talentCity)
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

        binding.ivTalentEmail.setOnClickListener {
            Toast.makeText(this@TalentProfileActivity, "Your Email is: $accountEmail", Toast.LENGTH_SHORT).show()
        }

        binding.ivTalentPhone.setOnClickListener {
            Toast.makeText(this@TalentProfileActivity, "Your Phone Number is: $accountPhone", Toast.LENGTH_SHORT).show()
        }

        binding.ivTalentGithub.setOnClickListener {
            Toast.makeText(this@TalentProfileActivity, "Your Github is: https://github.com/$talentGithub", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setRefreshManager() {
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                presenter?.getTalentData(talentID!!)
                handler.postDelayed(this, 2000)
            }
        })
    }

    override fun setError(error: String) {
        when (error) {
            "Session Expired !" -> {
                sessionExpiredAlert()
            }

            else -> {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun hideProgressBar() {
        binding.loadingScreen.visibility = View.GONE
    }

    override fun sessionExpiredAlert() {
        handler.removeCallbacksAndMessages(null)
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

    override fun setProfileTab() {
        pagerAdapter = TalentProfileTabAdapter(supportFragmentManager)
        binding.vpTalentProfile.adapter = pagerAdapter
        binding.tabTalentProfile.setupWithViewPager(binding.vpTalentProfile)

    }

    override fun setFloatingButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}