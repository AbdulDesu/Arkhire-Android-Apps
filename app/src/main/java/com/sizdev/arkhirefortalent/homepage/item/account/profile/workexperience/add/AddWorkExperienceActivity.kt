package com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience.add

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityAddWorkExperienceBinding
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService

class AddWorkExperienceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkExperienceBinding
    private lateinit var viewModel: AddWorkExperienceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_work_experience)
        viewModel = ViewModelProvider(this).get(AddWorkExperienceViewModel::class.java)

        val service = ArkhireApiClient.getApiClient(this)?.create(ArkhireApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        binding.btNewExperienceDone.setOnClickListener {
            if(binding.etNewExperiencePosition.text.isEmpty() || binding.etExperienceSource.text.isEmpty() || binding.etExperienceStart.text.isEmpty() || binding.etExperienceEnd.text.isEmpty()){
                Toast.makeText(this, "Please Fill All Required Field", Toast.LENGTH_SHORT).show()
            }
            else{
                // Get Saved Account
                val sharedPrefData = this.getSharedPreferences("Token", Context.MODE_PRIVATE)
                val savedID = sharedPrefData.getString("accID", null)

                viewModel.addWorkExperience(savedID!!, binding.etNewExperiencePosition.text.toString(), binding.etExperienceSource.text.toString(), binding.etExperienceStart.text.toString(), binding.etExperienceEnd.text.toString(), binding.etExperienceDesc.text.toString())
            }

            subscribeLiveData()
        }
    }

    private fun subscribeLiveData() {
        viewModel.isSuccess.observe(this, {
            if (viewModel.isSuccess.value == "Success") {
                Toast.makeText(this, "Experiences Added to your profile !", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed! To Add Experiences", Toast.LENGTH_LONG).show()
            }
        })
    }
}