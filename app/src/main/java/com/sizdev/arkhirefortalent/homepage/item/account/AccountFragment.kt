package com.sizdev.arkhirefortalent.homepage.item.account

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.password.ResetPasswordActivity
import com.sizdev.arkhirefortalent.databinding.FragmentAccountBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.TalentProfileActivity
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditProfileActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_logout_confirmation.view.*
import kotlinx.coroutines.*

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var dialog: AlertDialog
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ArkhireApiClient.getApiClient(it) }!!.create(ArkhireApiService::class.java)

        // Data Loading Management
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100

        // Get Saved Name
        val sharedPrefData = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
        val savedID = sharedPrefData.getString("accID", null)

        // Get Saved Data
        if (savedID != null) {
            showAccountData(savedID)
        }

        binding.tvLogout.setOnClickListener {
            startAlertLogoutConfirmation()
            dialog.show()
        }

        binding.tvMyProfile.setOnClickListener {
            Toast.makeText(activity, "Loading, Please Wait..", Toast.LENGTH_LONG).show()
        }

        binding.tvMyPassword.setOnClickListener {
            val intent = Intent(activity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        return  binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun showAccountData(talentName: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getAccountDataByNameResponse(talentName)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is AccountResponse) {
                //Set Data
                binding.tvFullNameAccount.text = result.data[0].accountName
                when(result.data[0].talentTitle){
                    null -> binding.tvTitleAccount.text = "Let's start exploring the world !"
                    else -> binding.tvTitleAccount.text = result.data[0].talentTitle
                }

                //Set Profile Images
                when (result.data[0].talentImage){

                    null -> binding.ivProfileImage.setImageResource(R.drawable.ic_empty_image)

                    else -> {
                        Picasso.get()
                                .load("http://54.82.81.23:911/image/${result.data[0].talentImage}")
                                .resize(512, 512)
                                .centerCrop()
                                .into(binding.ivProfileImage)
                    }
                }

                binding.tvMyProfile.setOnClickListener {
                    val intent = Intent(activity, TalentProfileActivity::class.java)
                    intent.putExtra("talentID", result.data[0].talentID)
                    intent.putExtra("accountID", result.data[0].accountID)
                    intent.putExtra("talentName", result.data[0].accountName)
                    intent.putExtra("talentEmail", result.data[0].accountEmail)
                    intent.putExtra("talentPhone", result.data[0].accountPhone)
                    intent.putExtra("talentTitle", result.data[0].talentTitle)
                    intent.putExtra("talentTime", result.data[0].talentTime)
                    intent.putExtra("talentLocation", result.data[0].talentCity)
                    intent.putExtra("talentDesc", result.data[0].talentDesc)
                    intent.putExtra("talentImage", result.data[0].talentImage)
                    intent.putExtra("talentGithub", result.data[0].talentGithub)
                    intent.putExtra("talentCv", result.data[0].talentCv)
                    intent.putExtra("talentSkill1", result.data[0].talentSkill1)
                    intent.putExtra("talentSkill2", result.data[0].talentSkill2)
                    intent.putExtra("talentSkill3", result.data[0].talentSkill3)
                    intent.putExtra("talentSkill4", result.data[0].talentSkill4)
                    intent.putExtra("talentSkill5", result.data[0].talentSkill5)

                    if (result.data[0].talentTitle == null){
                        val intent = Intent(activity, EditProfileActivity::class.java)
                        intent.putExtra("talentID", result.data[0].talentID)
                        startActivity(intent)
                    }
                    else {
                        startActivity(intent)
                    }
                }

                // End Of Loading
                Handler().postDelayed({
                    binding.loadingScreen.visibility = View.GONE
                }, 2000)
            }
        }
    }


    private fun startAlertLogoutConfirmation() {
        val view: View = layoutInflater.inflate(R.layout.alert_logout_confirmation, null)

        dialog = activity?.let {
            AlertDialog.Builder(it)
                    .setView(view)
                    .setCancelable(false)
                    .create()
        }!!

        view.bt_yesLogout.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(activity, LoginActivity::class.java)
            val sharedPref = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("accID", null)
            editor.apply()
            startActivity(intent)
            activity?.finish()
        }

        view.bt_noLogout.setOnClickListener {
            dialog.cancel()
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}