package com.sizdev.arkhirefortalent.administration.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.administration.login.LoginActivity
import com.sizdev.arkhirefortalent.databinding.ActivityRegisterBinding
import com.sizdev.arkhirefortalent.homepage.HomeActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.btRegister.setOnClickListener {
            val registerPassword = binding.etRegistPassword.text.toString()
            val confirmRegisterPassword = binding.etConfirmRegistPassword.text.toString()
            val fullName = binding.etFullName.text.toString()
            val registerEmail = binding.etRegistEmail.text.toString()
            val registerPhoneNumber = binding.etRegistPhone.text.toString()

            if (registerPassword != confirmRegisterPassword){
                Toast.makeText(this, "Password not match", Toast.LENGTH_LONG).show()
            }
            else if (registerPassword.isEmpty() || confirmRegisterPassword.isEmpty() || fullName.isEmpty() || registerEmail.isEmpty() || registerPhoneNumber.isEmpty()){
                Toast.makeText(this, "Please Fill All Field", Toast.LENGTH_LONG).show()
            }

            else {
                Toast.makeText(this, "Registered Succesfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                logedInSuccesfully()
                saveData()
                clearProfile()
                resetProfile()
                finish()
            }

        }

        binding.tvBackLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveData() {
        val fullName = binding.etFullName.text.toString()
        val registerEmail = binding.etRegistEmail.text.toString().toLowerCase()
        val registerPassword = binding.etRegistPassword.text.toString()
        val talentPhone = binding.etRegistPhone.text.toString()
        val sharedPrefData = getSharedPreferences("fullData", Context.MODE_PRIVATE)
        val editor = sharedPrefData.edit()
        editor.apply {
            putString("fullName", fullName)
            putString("talentEmail", registerEmail)
            putString("talentPassword", registerPassword)
            putString("talentPhone", talentPhone)
        }.apply()
    }

    private fun logedInSuccesfully(){
        val sharedPref = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Login", true)
        editor.apply()
    }

    private fun clearProfile(){
        val sharedPref = getSharedPreferences("newTalent", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("updatedProfile", false)
        editor.apply()
    }

    private fun resetProfile(){
        val sharedPref = getSharedPreferences("profileData", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("talentTitle", null)
            putString("talentLocation", null)
            putString("talentWorkTime", null)
            putString("talentDescription", null)
            putString("talentSkill1", null)
            putString("talentSkill2", null)
            putString("talentSkill3", null)
            putString("talentSkill4", null)
            putString("talentSkill5", null)
            putString("talentGithub", null)
        }.apply()
    }

}

