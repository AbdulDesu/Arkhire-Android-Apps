package com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.register.RegisterResponse
import com.sizdev.arkhirefortalent.databinding.ActivityProfileEditBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.create.CreatePortfolioActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.math.log

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var viewModel: EditProfileViewModel

    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        private const val PERMISSION_CODE = 1001;
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)

        val service = ArkhireApiClient.getApiClient(this)?.create(ArkhireApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        // Get Saved Data
        var talentID = intent.getStringExtra("talentID")
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

        //Set Image Default
        if(talentImage != null){
            Picasso.get()
                    .load("http://54.82.81.23:911/image/$talentImage")
                    .resize(512, 512)
                    .centerCrop()
                    .into(binding.ivEditProfileImage)
        }
        else{
            binding.ivEditProfileImage.setImageResource(R.drawable.ic_empty_image)
        }

        //Job Tittle Pop Up Menu
        val jobTitle = PopupMenu(this, binding.etNewProfileJobTitle)

        jobTitle.menu.add(Menu.NONE, 0 ,0, "Android Developer")
        jobTitle.menu.add(Menu.NONE, 1 ,1, "Fullstack Mobile")
        jobTitle.menu.add(Menu.NONE, 2 ,2, "Fullstack Web")
        jobTitle.menu.add(Menu.NONE, 3 ,3, "Devops Engineer")

        //Talent City Pop Up Menu
        val city = PopupMenu(this, binding.etNewProfileLocation)

        city.menu.add(Menu.NONE, 0 ,0, "Jakarta")
        city.menu.add(Menu.NONE, 1 ,1, "Bandung")
        city.menu.add(Menu.NONE, 2 ,2, "Lampung")
        city.menu.add(Menu.NONE, 3 ,3, "Bali")
        city.menu.add(Menu.NONE, 4 ,4, "Aceh")
        city.menu.add(Menu.NONE, 5 ,5, "Cimahi")
        city.menu.add(Menu.NONE, 6 ,6, "Nagreg")
        city.menu.add(Menu.NONE, 7 ,7, "Cicalengka")

        //Work Time Pop Up Menu
        val workTime = PopupMenu(this, binding.etNewProfileWorkTime)

        workTime.menu.add(Menu.NONE, 0 ,0, "FullTime")
        workTime.menu.add(Menu.NONE, 1 ,1, "Freelance")

        //Skill Choose Pop Up Menu
        val talentSKill1 = PopupMenu(this, binding.etNewProfileSkill1)

        talentSKill1.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill1.menu.add(Menu.NONE, 1 ,1, "HTML&CSS")
        talentSKill1.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill1.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill1.menu.add(Menu.NONE, 4 ,4, "React")
        talentSKill1.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill1.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill2 = PopupMenu(this, binding.etNewProfileSkill2)

        talentSKill2.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill2.menu.add(Menu.NONE, 1 ,1, "HTML&CSS")
        talentSKill2.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill2.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill2.menu.add(Menu.NONE, 4 ,4, "React")
        talentSKill2.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill2.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill3 = PopupMenu(this, binding.etNewProfileSkill3)

        talentSKill3.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill3.menu.add(Menu.NONE, 1 ,1, "HTML&CSS")
        talentSKill3.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill3.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill3.menu.add(Menu.NONE, 4 ,4, "React")
        talentSKill3.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill3.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill4 = PopupMenu(this, binding.etNewProfileSkill4)

        talentSKill4.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill4.menu.add(Menu.NONE, 1 ,1, "HTML&CSS")
        talentSKill4.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill4.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill4.menu.add(Menu.NONE, 4 ,4, "React")
        talentSKill4.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill4.menu.add(Menu.NONE, 6 ,6, "Golang")

        val talentSKill5 = PopupMenu(this, binding.etNewProfileSkill5)

        talentSKill5.menu.add(Menu.NONE, 0 ,0, "JavaScript")
        talentSKill5.menu.add(Menu.NONE, 1 ,1, "HTML&CSS")
        talentSKill5.menu.add(Menu.NONE, 2 ,2, "Java")
        talentSKill5.menu.add(Menu.NONE, 3 ,3, "Kotlin")
        talentSKill5.menu.add(Menu.NONE, 4 ,4, "React")
        talentSKill5.menu.add(Menu.NONE, 5 ,5, "Python")
        talentSKill5.menu.add(Menu.NONE, 6 ,6, "Golang")

        //Job Title Listener
        jobTitle.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileJobTitle.text ="Android Developer"}
                1 -> {binding.etNewProfileJobTitle.text ="Fullstack Mobile"}
                2 -> {binding.etNewProfileJobTitle.text ="Fullstack Web"}
                3 -> {binding.etNewProfileJobTitle.text ="Devops Engineer"}
            }

            false
        }

        //City Listener
        city.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileLocation.text = "Jakarta"}
                1 -> {binding.etNewProfileLocation.text = "Bandung"}
                2 -> {binding.etNewProfileLocation.text = "Lampung"}
                3 -> {binding.etNewProfileLocation.text = "Bali"}
                4 -> {binding.etNewProfileLocation.text = "Aceh"}
                5 -> {binding.etNewProfileLocation.text = "Cimahi"}
                6 -> {binding.etNewProfileLocation.text = "Nagreg"}
                7 -> {binding.etNewProfileLocation.text = "Cicalengka"}
            }

            false
        }

        //Work Time Listener
        workTime.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileWorkTime.text = "Fulltime"}
                1 -> {binding.etNewProfileWorkTime.text = "Freelance"}
            }

            false
        }

        //Talent Skill 1 Listener
        talentSKill1.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill1.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill1.text = "HTML&CSS"}
                2 -> {binding.etNewProfileSkill1.text = "Java"}
                3 -> {binding.etNewProfileSkill1.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill1.text = "React"}
                5 -> {binding.etNewProfileSkill1.text = "Python"}
                6 -> {binding.etNewProfileSkill1.text = "Golang"}
            }

            false
        }

        //Talent Skill 2 Listener
        talentSKill2.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill2.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill2.text = "HTML&CSS"}
                2 -> {binding.etNewProfileSkill2.text = "Java"}
                3 -> {binding.etNewProfileSkill2.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill2.text = "React "}
                5 -> {binding.etNewProfileSkill2.text = "Python"}
                6 -> {binding.etNewProfileSkill2.text = "Golang"}
            }

            false
        }

        //Talent Skill 3 Listener
        talentSKill3.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill3.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill3.text = "HTML&CSS"}
                2 -> {binding.etNewProfileSkill3.text = "Java"}
                3 -> {binding.etNewProfileSkill3.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill3.text = "React"}
                5 -> {binding.etNewProfileSkill3.text = "Python"}
                6 -> {binding.etNewProfileSkill3.text = "Golang"}
            }

            false
        }

        //Talent Skill 4 Listener
        talentSKill4.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill4.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill4.text = "HTML&CSS"}
                2 -> {binding.etNewProfileSkill4.text = "Java"}
                3 -> {binding.etNewProfileSkill4.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill4.text = "React"}
                5 -> {binding.etNewProfileSkill4.text = "Python"}
                6 -> {binding.etNewProfileSkill4.text = "Golang"}
            }

            false
        }

        //Talent Skill 5 Listener
        talentSKill5.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            when (id) {
                0 -> {binding.etNewProfileSkill5.text = "JavaScript"}
                1 -> {binding.etNewProfileSkill5.text = "HTML&CSS"}
                2 -> {binding.etNewProfileSkill5.text = "Java"}
                3 -> {binding.etNewProfileSkill5.text = "Kotlin"}
                4 -> {binding.etNewProfileSkill5.text = "React"}
                5 -> {binding.etNewProfileSkill5.text = "Python"}
                6 -> {binding.etNewProfileSkill5.text = "Golang"}
            }

            false
        }

        //Show Pop Up when clicked
        binding.etNewProfileJobTitle.setOnClickListener {
            jobTitle.show()
        }

        binding.etNewProfileLocation.setOnClickListener {
            city.show()
        }

        binding.etNewProfileWorkTime.setOnClickListener {
           workTime.show()
        }

        binding.etNewProfileSkill1.setOnClickListener {
            talentSKill1.show()
        }

        binding.etNewProfileSkill2.setOnClickListener {
            talentSKill2.show()
        }

        binding.etNewProfileSkill3.setOnClickListener {
            talentSKill3.show()
        }

        binding.etNewProfileSkill4.setOnClickListener {
            talentSKill4.show()
        }

        binding.etNewProfileSkill5.setOnClickListener {
            talentSKill5.show()
        }

        binding.btEditProfileImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, EditProfileActivity.PERMISSION_CODE);
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

        binding.btNewProfileDone.setOnClickListener {

            if(binding.etNewProfileJobTitle.text.isEmpty() || binding.etNewProfileWorkTime.text.isEmpty() || binding.etNewProfileLocation.text.isEmpty() || binding.etNewProfileDesc.text.isEmpty()){
                Toast.makeText(this, "Please Fill All Field", Toast.LENGTH_LONG).show()
            }
            else{
                if(talentImage != "null"){
                    /*val savedTalentTitle = createPartFromString(binding.etNewProfileJobTitle.text.toString())
                    val savedTalentTime = createPartFromString(binding.etNewProfileWorkTime.text.toString())
                    val savedTalentCity = createPartFromString(binding.etNewProfileLocation.text.toString())
                    val savedTalentDesc = createPartFromString(binding.etNewProfileDesc.text.toString())
                    val savedTalentImage = createPartFromString(talentImage!!)
                    viewModel.updateBasicInfo(talentID!!, savedTalentTitle, savedTalentTime, savedTalentCity, savedTalentDesc, savedTalentImage)*/
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            EditProfileActivity.PERMISSION_CODE -> {
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
        startActivityForResult(intent, EditProfileActivity.IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == EditProfileActivity.IMAGE_PICK_CODE) {
            binding.ivEditProfileImage.setImageURI(data?.data)

            var talentID = intent.getStringExtra("talentID")

            val filePath = data?.data?.let { getPath(this, it) }
            val file = File(filePath)

            val talentTitle = createPartFromString(binding.etNewProfileJobTitle.text.toString())
            val talentTime = createPartFromString(binding.etNewProfileWorkTime.text.toString())
            val talentCity = createPartFromString(binding.etNewProfileLocation.text.toString())
            val talentDesc = createPartFromString(binding.etNewProfileDesc.text.toString())
            var talentImage: MultipartBody.Part? = null
            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = data?.data?.let { contentResolver.openInputStream(it) }
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)

            talentImage = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("talent_image", file.name, it1)
            }

            binding.btNewProfileDone.setOnClickListener {
                if(binding.etNewProfileJobTitle.text.isEmpty() || binding.etNewProfileWorkTime.text.isEmpty() || binding.etNewProfileLocation.text.isEmpty() || binding.etNewProfileDesc.text.isEmpty()){
                    Toast.makeText(this, "Please Fill All Field!", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (talentImage != null) {
                    viewModel.updateBasicInfo(talentID!!, talentTitle, talentTime, talentCity, talentDesc, talentImage)
                    viewModel.updateSkill(talentID!!, binding.etNewProfileSkill1.text.toString(), binding.etNewProfileSkill2.text.toString(), binding.etNewProfileSkill3.text.toString(), binding.etNewProfileSkill4.text.toString(), binding.etNewProfileSkill5.text.toString())
                    }
                }
            }

            subscribeLiveData()
        }
    }

    fun getPath(context: Context, contentUri: Uri) : String? {
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

    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
            .toRequestBody(mediaType)
    }

    private fun subscribeLiveData() {
        viewModel.isSuccess.observe(this, {
            if (viewModel.isSuccess.value == "Success") {
                Toast.makeText(this, "Profile Updated !", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed, To Update !", Toast.LENGTH_LONG).show()
            }
        })
    }


}