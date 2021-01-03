package com.sizdev.arkhirefortalent.homepage.item.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.password.ResetPasswordActivity
import com.sizdev.arkhirefortalent.databinding.FragmentAccountBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.TalentProfileActivity
import com.sizdev.arkhirefortalent.networking.ApiClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_logout_confirmation.view.*
import kotlinx.coroutines.*

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var dialog: AlertDialog
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: AccountApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ApiClient.getApiClient(it) }!!.create(AccountApiService::class.java)

        // Get Saved Name
        val sharedPrefData = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
        val savedName = sharedPrefData.getString("accName", null)

        // Get Saved Data
        if (savedName != null) {
            showAccountData(savedName)
        }

        binding.tvLogout.setOnClickListener {
            startAlertLogoutConfirmation()
            dialog.show()
        }

        binding.tvMyProfile.setOnClickListener {
            val intent = Intent(activity, TalentProfileActivity::class.java)
            startActivity(intent)

        }

        binding.tvMyPassword.setOnClickListener {
            val intent = Intent(activity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        return  binding.root
    }

    private fun showAccountData(talentName: String) {
        coroutineScope.launch {
            Log.d("Arkhire Talent", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("Arkhire Talent", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getAccountDataByNameResponse(talentName)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is AccountResponse) {
                binding.tvFullNameAccount.text = result.data[0].accountName
                binding.tvTitleAccount.text = result.data[0].talentTitle

                //Set Profile Images
                Picasso.get()
                        .load("http://54.82.81.23:911/image/${result.data[0].talentImage}")
                        .resize(512, 512)
                        .centerCrop()
                        .into(binding.ivProfileImage)
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