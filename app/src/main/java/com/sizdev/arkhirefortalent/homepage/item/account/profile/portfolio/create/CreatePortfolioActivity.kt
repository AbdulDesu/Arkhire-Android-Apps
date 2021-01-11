package com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.create

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityCreatePortfolioBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CreatePortfolioActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityCreatePortfolioBinding
    private lateinit var  viewModel: CreatePortfolioViewModel

    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        private const val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_portfolio)
        viewModel = ViewModelProvider(this).get(CreatePortfolioViewModel::class.java)

        val service = ArkhireApiClient.getApiClient(this)?.create(ArkhireApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        binding.btPickImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
             }
        }

        binding.btNewPortfolioDone.setOnClickListener {
            if(binding.etNewPortfolioTitle.text.isEmpty() && binding.etNewPortfolioDesc.text.isEmpty()) {
                Toast.makeText(this, "Please Fill Title, Image, And Desc of this portfolio!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "For Best Result, Pick Image Of This Portfolio!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Please Allow Permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.ivEditPortfolioImage.setImageURI(data?.data)

            // Get Saved Account
            val sharedPrefData = this.getSharedPreferences("Token", Context.MODE_PRIVATE)
            val savedID = sharedPrefData.getString("accID", null)
            val portfolioOwner = createPartFromString(savedID!!)

            val filePath = data?.data?.let { getPath(this, it) }
            val file = File(filePath)

            val portfolioName = createPartFromString(binding.etNewPortfolioTitle.text.toString())
            val portfolioDesc = createPartFromString(binding.etNewPortfolioDesc.text.toString())
            val portfolioRepository = createPartFromString(binding.etNewPortfolioRepository.text.toString())
            var portfolioImage: MultipartBody.Part? = null
            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = data?.data?.let { contentResolver.openInputStream(it) }
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)

            portfolioImage = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("portfolio_image", file.name, it1)
            }

            binding.btNewPortfolioDone.setOnClickListener {
                if(binding.etNewPortfolioTitle.text.isEmpty() && binding.etNewPortfolioDesc.text.isEmpty()){
                    Toast.makeText(this, "Please Fill Title, And Desc of this portfolio!", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (portfolioImage != null) {
                        viewModel.uploadPortfolio(portfolioOwner, portfolioName, portfolioDesc, portfolioRepository, portfolioImage)
                    }
                }
            }

            subscribeLiveData()
        }
    }

    fun getPath(context: Context, contentUri: Uri) : String? {
        var result: String? = null
        val portfolio = arrayOf(MediaStore.Images.Media.DATA)

        val cursorLoader = CursorLoader(context, contentUri, portfolio, null, null, null)
        val cursor = cursorLoader.loadInBackground()

        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(columnIndex)
            cursor.close()
        }
        return result
    }

    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
            .toRequestBody(mediaType)
    }

    private fun subscribeLiveData() {
        viewModel.isSuccess.observe(this, {
            if (viewModel.isSuccess.value == "Success") {
                Toast.makeText(this, "Portfolio Added to your profile !", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed! To Upload", Toast.LENGTH_LONG).show()
            }
        })
    }
}