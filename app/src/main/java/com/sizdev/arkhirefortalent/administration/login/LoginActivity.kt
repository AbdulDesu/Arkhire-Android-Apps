package com.sizdev.arkhirefortalent.administration.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.email.ResetEmailActivity
import com.sizdev.arkhirefortalent.administration.register.RegisterActivity
import com.sizdev.arkhirefortalent.databinding.ActivityLoginBinding
import com.sizdev.arkhirefortalent.homepage.HomeActivity
import com.sizdev.arkhirefortalent.networking.ArkhireApiClient
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Set Status Bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        // Set Service
        setService()

        // Observe Data
        subscribeLiveData()

        binding.btLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().toLowerCase()
            val password = binding.etLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please Fill Email & Password", Toast.LENGTH_LONG).show()
            }

            else {
                viewModel.startLogin(email, password)
            }
        }

        binding.tvGoRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.tvForgetPassword.setOnClickListener {
            Toast.makeText(this, "Currently Unavailable", Toast.LENGTH_SHORT).show()
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
                if(viewModel.loginData.value?.privilege == "0"){
                    Toast.makeText(this@LoginActivity, "Welcome Back !", Toast.LENGTH_SHORT).show()

                    // Save Token
                    val sharedPref = this@LoginActivity.getSharedPreferences("Token", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("accToken", viewModel.loginData.value?.token)
                    editor.putString("accID", viewModel.loginData.value?.userId)
                    editor.putString("accName", viewModel.loginData.value?.userName)
                    editor.putString("accEmail", viewModel.loginData.value?.userEmail)
                    editor.putString("accPassword", binding.etLoginPassword.text.toString())
                    editor.apply()

                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                else {
                    // Stop Loading
                    binding.loadingScreen.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "Please use company version of Arkhire !", Toast.LENGTH_LONG).show()
                }
            }

            else{
                // Stop Loading
                binding.loadingScreen.visibility = View.GONE
            }
        })

        viewModel.onFail.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }
}

