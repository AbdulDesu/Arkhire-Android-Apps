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
import com.sizdev.arkhirefortalent.homepage.item.account.profile.curiculumvitae.CurriculumVitaeActivity
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditProfileActivity
import com.sizdev.arkhirefortalent.homepage.item.account.profile.previewer.TalentPreviewActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.sizdev.arkhirefortalent.webviewer.ArkhireWebViewerActivity
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

    override fun setTalentData(talentID: String?, accountID: String?,accountName: String?, accountEmail: String?, accountPhone: String?, talentTitle: String?, talentTime: String?, talentCity: String?, talentDesc: String?, talentImage: String?, talentGithub: String?, talentCv: String?, talentSkill1: String?, talentSkill2: String?, talentSkill3: String?, talentSkill4: String?, talentSkill5: String?) {
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
            binding.btWorkTime.setImageResource(R.drawable.ic_time_freelancer)
            binding.btWorkTime.setOnClickListener {
                Toast.makeText(this, "You Are Freelancer", Toast.LENGTH_LONG).show()
            }
        } else {
            binding.ivTalentProfileCover.setImageResource(R.drawable.ic_fulltimework)
            binding.btWorkTime.setImageResource(R.drawable.ic_worktime)
            binding.btWorkTime.setOnClickListener {
                Toast.makeText(this, "You Are Full Time Worker", Toast.LENGTH_LONG).show()
            }
        }

        //Validate Skill Null or not
        if (talentSkill1 == null || talentSkill1 == "") {
            binding.tvTitleProfileTalentSkill1.visibility = View.INVISIBLE
        } else {
            binding.tvTitleProfileTalentSkill1.text = talentSkill1
        }

        if (talentSkill2 == null || talentSkill2 == "") {
            binding.tvTitleProfileTalentSkill2.visibility = View.INVISIBLE
        } else {
            binding.tvTitleProfileTalentSkill2.text = talentSkill2
        }

        if (talentSkill3 == null || talentSkill3 == "") {
            binding.tvTitleProfileTalentSkill3.visibility = View.INVISIBLE
        } else {
            binding.tvTitleProfileTalentSkill3.text = talentSkill3
        }

        if (talentSkill4 == null || talentSkill4 == "") {
            binding.tvTitleProfileTalentSkill4.visibility = View.INVISIBLE
        } else {
            binding.tvTitleProfileTalentSkill4.text = talentSkill4
        }

        if (talentSkill5 == null || talentSkill5 == "") {
            binding.tvTitleProfileTalentSkill5.visibility = View.INVISIBLE
        } else {
            binding.tvTitleProfileTalentSkill5.text = talentSkill5
        }

        binding.menuButton.setOnClickListener {
            val showMenu = PopupMenu(this@TalentProfileActivity, binding.menuButton)
            showMenu.menu.add(Menu.NONE, 0, 0, "Edit CV")
            showMenu.menu.add(Menu.NONE, 1, 1, "Preview CV")
            showMenu.menu.add(Menu.NONE, 2, 2, "Edit Profile")
            showMenu.menu.add(Menu.NONE, 3, 3, "Preview Profile")
            showMenu.show()

            showMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {

                    0 -> {
                        val intent = Intent(this, CurriculumVitaeActivity::class.java)
                        intent.putExtra("talentID", talentID)
                        intent.putExtra("talentCv", talentCv)
                        startActivity(intent)
                    }

                    1 -> {
                        val intent =Intent(this, ArkhireWebViewerActivity::class.java)
                        intent.putExtra("url", "http://54.82.81.23:911/image/$talentCv")
                        intent.putExtra("webScale", "70")
                        startActivity(intent)
                    }

                    2 -> {
                        val intent = Intent(this@TalentProfileActivity, EditProfileActivity::class.java)
                        intent.putExtra("talentID", talentID)
                        intent.putExtra("talentLocation", talentCity)
                        intent.putExtra("talentTitle", talentTitle)
                        intent.putExtra("talentDesc", talentDesc)
                        intent.putExtra("talentImage", talentImage)
                        intent.putExtra("talentWorkTime", talentTime)
                        intent.putExtra("talentGithub", talentGithub)
                        intent.putExtra("talentSkill1", talentSkill1)
                        intent.putExtra("talentSkill2", talentSkill2)
                        intent.putExtra("talentSkill3", talentSkill3)
                        intent.putExtra("talentSkill4", talentSkill4)
                        intent.putExtra("talentSkill5", talentSkill5)
                        startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent(this, TalentPreviewActivity::class.java)
                        intent.putExtra("talentID", talentID)
                        intent.putExtra("guestAccountID", accountID)
                        intent.putExtra("previewCode", "owner")
                        intent.putExtra("talentCv", talentCv)
                        startActivity(intent)
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

        when(talentGithub) {
            "" -> binding.ivTalentGithub.setImageResource(R.drawable.ic_github_disabled)
            null -> binding.ivTalentGithub.setImageResource(R.drawable.ic_github_disabled)
            else -> {
                binding.ivTalentGithub.setOnClickListener {
                    Toast.makeText(this@TalentProfileActivity, "Your Github is: https://github.com/$talentGithub", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.ivHelpAboutYou.setOnClickListener {
            Toast.makeText(this, "Tell Company About Your Profile", Toast.LENGTH_SHORT).show()
        }

        binding.ivHelpSkillYou.setOnClickListener {
            Toast.makeText(this, "Tell Company About Your Skill", Toast.LENGTH_SHORT).show()
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