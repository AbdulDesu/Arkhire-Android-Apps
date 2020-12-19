package com.sizdev.arkhirefortalent.administration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityLoginBinding
import com.sizdev.arkhirefortalent.homepage.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // Get Registered Talent Data
        val sharedPrefData = this.getSharedPreferences("fullData", Context.MODE_PRIVATE)
        val registeredEmail = sharedPrefData.getString("talentEmail", null)
        val registeredPassword = sharedPrefData.getString("talentPassword", null)

        binding.btLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if(email == registeredEmail && password == registeredPassword){
                Toast.makeText(this, "Welcome Back", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                logedInSuccesfully()
                finish()
            }

            else if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(this, "Please Fill Email & Password", Toast.LENGTH_LONG).show()
            }

            else {
                Toast.makeText(this, "Invalid Email / Password", Toast.LENGTH_LONG).show()
            }

        }

        binding.tvRegisterScreen.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.tvForgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
    private fun logedInSuccesfully(){
        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Login", true)
        editor.apply()
    }

}

