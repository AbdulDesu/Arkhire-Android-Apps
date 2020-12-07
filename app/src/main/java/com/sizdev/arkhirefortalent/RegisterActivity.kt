package com.sizdev.arkhirefortalent

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.sizdev.arkhirefortalent.homepage.HomeActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        bt_register.setOnClickListener {
            val registerPassword = et_registPassword.text.toString()
            val confirmRegisterPassword = et_confirmRegistPassword.text.toString()
            val fullName = et_fullName.text.toString()
            val registerEmail = et_registEmail.text.toString()
            val registerPhoneNumber = et_registPhone.text.toString()

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

        tv_backLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveData() {
        val fullName = et_fullName.text.toString()
        val registerEmail = et_registEmail.text.toString()
        val registerPassword = et_registPassword.text.toString()
        val sharedPrefData = getSharedPreferences("fullData", Context.MODE_PRIVATE)
        val editor = sharedPrefData.edit()
        editor.apply {
            putString("fullName", fullName)
            putString("talentEmail", registerEmail)
            putString("talentPassword", registerPassword)
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

