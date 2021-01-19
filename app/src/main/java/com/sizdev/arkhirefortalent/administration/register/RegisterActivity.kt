package com.sizdev.arkhirefortalent.administration.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.databinding.ActivityRegisterBinding
import com.sizdev.arkhirefortalent.homepage.HomeActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // Set Service
        setService()
        subscribeLiveData()

        binding.btRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString().capitalize(Locale.ROOT)
            val registerEmail = binding.etRegistEmail.text.toString()
            val registerPhoneNumber = binding.etRegistPhone.text.toString()
            val registerPassword = binding.etRegistPassword.text.toString()
            val confirmRegisterPassword = binding.etConfirmRegistPassword.text.toString()

             if (registerPassword.isEmpty() || confirmRegisterPassword.isEmpty() || fullName.isEmpty() || registerEmail.isEmpty() || registerPhoneNumber.isEmpty()){
                Toast.makeText(this, "Please Fill All Field", Toast.LENGTH_LONG).show()
            }
            else {
                 if (registerPassword != confirmRegisterPassword){
                     Toast.makeText(this, "Password not match", Toast.LENGTH_LONG).show()
                 }
                 else {
                     if (registerPassword.length >= 8){
                         viewModel.startRegister(fullName, registerEmail, registerPhoneNumber, registerPassword, 0)
                     }
                     else {
                         Toast.makeText(this, "Password Must Contain 8 Character !", Toast.LENGTH_SHORT).show()
                     }
                 }
            }
        }

        binding.tvBackLogin.setOnClickListener {
            finish()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setService() {
        val service = ArkhireApiClient.getApiClient(this)?.create(ArkhireApiService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }
    }

    private fun subscribeLiveData() {
        viewModel.isLoading.observe(this, {
            binding.loadingScreen.visibility = View.VISIBLE
        })

        viewModel.onSuccess.observe(this, {
            if (it) {
                Toast.makeText(this@RegisterActivity, "Registered Succesfully, Please Login To Continue", Toast.LENGTH_LONG).show()
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            else {
                // Stop Loading
                binding.loadingScreen.visibility = View.GONE
            }
        })

        viewModel.onFail.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }
}

