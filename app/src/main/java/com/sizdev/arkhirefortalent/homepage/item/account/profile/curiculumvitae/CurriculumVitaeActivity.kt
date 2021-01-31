package com.sizdev.arkhirefortalent.homepage.item.account.profile.curiculumvitae

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityTalentCuriculumVitaeBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditProfileActivity
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditProfileViewModel
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CurriculumVitaeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalentCuriculumVitaeBinding
    private lateinit var viewModel: CurriculumVitaeViewModel

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_talent_curiculum_vitae)

        // Set Service
        setService()

        // Observe Live Data
        subscribeLiveData()

        // Set Default Image
        when(intent.getStringExtra("talentCv")){
            "" -> Toast.makeText(this, "Pick Image To Finish Editing !", Toast.LENGTH_SHORT).show()
            null -> Toast.makeText(this, "Pick Image To Finish Editing !", Toast.LENGTH_SHORT).show()
            else -> {
                Picasso.get()
                    .load("http://54.82.81.23:911/image/${intent.getStringExtra("talentCv")}")
                    .resize(720, 1080)
                    .centerCrop()
                    .into(binding.ivCv)
            }
        }

        binding.btPickImage.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                //show popup to request runtime permission
                requestPermissions(permissions, CurriculumVitaeActivity.PERMISSION_CODE)
            }
            else{
                //permission already granted
                pickImageFromGallery()
            }
        }
    }

    private fun setService() {
        viewModel = ViewModelProvider(this).get(CurriculumVitaeViewModel::class.java)
        val service = ArkhireApiClient.getApiClient(this)?.create(ArkhireApiService::class.java)
        viewModel.setService(service!!)
    }

    private fun subscribeLiveData() {
        viewModel.isLoading.observe(this, {
            binding.loadingScreen.visibility = View.VISIBLE
        })

        viewModel.isSuccess.observe(this, {
            if (viewModel.isSuccess.value == "Success") {
                Toast.makeText(this, "Profile Updated !", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed, To Update !", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, CurriculumVitaeActivity.IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CurriculumVitaeActivity.IMAGE_PICK_CODE) {
            binding.ivCv.setImageURI(data?.data)

            val filePath = data?.data?.let { getPath(this, it) }
            val file = File(filePath)

            var talentCv: MultipartBody.Part? = null
            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = data?.data?.let { contentResolver.openInputStream(it) }
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)

            talentCv = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("talent_cv", file.name, it1)
            }

            if (talentCv != null){
                viewModel.updateCurriculumVitae(intent.getStringExtra("talentID")!!, talentCv)
            }
        }
    }

    private fun getPath(context: Context, contentUri: Uri) : String? {
        var result: String? = null
        val data = arrayOf(MediaStore.Images.Media.DATA)

        val cursorLoader = CursorLoader(context, contentUri, data, null, null, null)
        val cursor = cursorLoader.loadInBackground()

        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(columnIndex)
            cursor.close()
        }
        return result
    }
}