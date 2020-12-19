package com.sizdev.arkhirefortalent.homepage.item

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sizdev.arkhirefortalent.administration.LoginActivity
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.ResetPasswordActivity
import com.sizdev.arkhirefortalent.homepage.profile.TalentProfileActivity

import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*



class AccountFragment : Fragment() {

    companion object {
        private  val REQUEST_CODE = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)

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

        view.tv_fullNameAccount.text = savedName
        view.tv_titleAccount.text = talentTitle

        view.tv_logout.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            logedOutSuccesfully()
            activity?.finish()
        }

        view.tv_myProfile.setOnClickListener {

            val intent = Intent(activity, TalentProfileActivity::class.java)
            startActivity(intent)

        }

        view.profile_image.setOnClickListener {
            pickImageFromGallery()
        }

        view.tv_myPassword.setOnClickListener {
            val intent = Intent(activity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        return  view
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

            profile_image.setImageBitmap(bitmap)

            profile_image.alpha = 0f
        }
    }
}