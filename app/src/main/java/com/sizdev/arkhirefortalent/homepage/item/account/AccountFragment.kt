package com.sizdev.arkhirefortalent.homepage.item.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.sizdev.arkhirefortalent.homepage.profile.TalentProfileActivity
import kotlinx.android.synthetic.main.alert_logout_confirmation.view.*

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var dialog: AlertDialog

    companion object {
        private  val REQUEST_CODE = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

        // Get Saved Name
        val sharedPrefData = requireActivity().getSharedPreferences(
            "fullData",
            Context.MODE_PRIVATE
        )
        val sharedPrefProfileData = requireActivity().getSharedPreferences(
            "profileData",
            Context.MODE_PRIVATE
        )
        val savedName = sharedPrefData.getString("fullName", null)
        val talentTitle = sharedPrefProfileData.getString("talentTitle", null)

        binding.tvFullNameAccount.text = savedName
        binding.tvTitleAccount.text = talentTitle

        binding.tvLogout.setOnClickListener {
            startAlertLogoutConfirmation()
            dialog.show()
        }

        binding.tvMyProfile.setOnClickListener {
            val intent = Intent(activity, TalentProfileActivity::class.java)
            startActivity(intent)

        }

        binding.profileImage.setOnClickListener {
            pickImageFromGallery()
        }

        binding.tvMyPassword.setOnClickListener {
            val intent = Intent(activity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        return  binding.root
    }

    private fun logedOutSuccesfully(){
        val sharedPref = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Login", false)
        editor.apply()
    }

    private fun profileUpdated(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("newTalent", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("updatedProfile", false)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    var selectedPhotoUri : Uri?=null
    var resolver = activity?.contentResolver

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(resolver, selectedPhotoUri)

            binding.profileImage.setImageBitmap(bitmap)

            binding.profileImage.alpha = 0f
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
            logedOutSuccesfully()
            startActivity(intent)
        }

        view.bt_noLogout.setOnClickListener {
            dialog.cancel()
        }
    }
}