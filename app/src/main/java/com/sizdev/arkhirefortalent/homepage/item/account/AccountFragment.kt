package com.sizdev.arkhirefortalent.homepage.item.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.administration.password.ResetPasswordActivity
import com.sizdev.arkhirefortalent.databinding.FragmentAccountBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_logout_confirmation.view.*
import kotlinx.android.synthetic.main.alert_session_expired.view.*
import kotlinx.coroutines.*

class AccountFragment : Fragment(), AccountContract.View {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var dialog: AlertDialog
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ArkhireApiService
    private lateinit var handler: Handler

    private var accountID: String? =null
    private var presenter: AccountPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

        // Set Service
        setService()

        // Show Progress Bar
        showProgressBar()

        // Get Current Login Data
        getCurrentLoginData()

        // Set Data Refresh Management
        setRefreshManager()

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

    override fun addAccountData(AccountName: String?, AccountTitle: String?, accountImage: String?) {
        binding.tvFullNameAccount.text = AccountName
        binding.tvTitleAccount.text = AccountTitle

        when (accountImage){
            null -> binding.ivProfileImage.setImageResource(R.drawable.ic_empty_image)

            else -> {
                Picasso.get()
                        .load("http://54.82.81.23:911/image/$accountImage")
                        .resize(512, 512)
                        .centerCrop()
                        .into(binding.ivProfileImage)
            }
        }
    }

    override fun setRefreshManager() {
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                presenter?.getAccountData(accountID!!)
                handler.postDelayed(this, 10000)
            }
        })
    }

    override fun setService() {
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = activity?.let { ArkhireApiClient.getApiClient(it) }!!.create(ArkhireApiService::class.java)
        presenter = AccountPresenter(coroutineScope, service)
    }

    override fun setError(error: String) {
        when (error) {
            "Session Expired !" -> {
                showSessionExpired()
            }

            else -> {
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getCurrentLoginData() {
        val sharedPrefData = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
        accountID = sharedPrefData.getString("accID", null)
    }

    override fun showProgressBar() {
        binding.loadingScreen.visibility = View.VISIBLE
        binding.progressBar.max = 100
    }

    override fun hideProgressBar() {
        binding.loadingScreen.visibility = View.GONE
    }

    override fun showSessionExpired() {
        handler.removeCallbacksAndMessages(null)
        val view: View = layoutInflater.inflate(R.layout.alert_session_expired, null)

        dialog = activity?.let {
            AlertDialog.Builder(it)
                    .setView(view)
                    .setCancelable(false)
                    .create()
        }!!

        view.bt_okRelog.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(activity, LoginActivity::class.java)
            val sharedPref = requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("accID", null)
            editor.apply()
            startActivity(intent)
            activity?.finish()
        }
    }
}