package com.sizdev.arkhirefortalent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sizdev.arkhirefortalent.homepage.HomeActivity
import com.sizdev.arkhirefortalent.homepage.item.HomeFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Get Registered Talent Data
        val sharedPrefData = requireActivity().getSharedPreferences("fullData", Context.MODE_PRIVATE)
        val registeredEmail = sharedPrefData.getString("talentEmail", null)
        val registeredPassword = sharedPrefData.getString("talentPassword", null)

        view.bt_login.setOnClickListener {
            val email = et_loginEmail.text.toString()
            val password = et_loginPassword.text.toString()

            if(email == registeredEmail && password == registeredPassword){
                Toast.makeText(activity, "Welcome Back", Toast.LENGTH_LONG).show()
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                logedInSuccesfully()
                activity?.finish()
            }

            else if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(activity, "Email & Password is empty", Toast.LENGTH_LONG).show()
            }

            else {
                Toast.makeText(activity, "Invalid Email / Password", Toast.LENGTH_LONG).show()
            }

        }

        view.tv_registerScreen.setOnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            startActivity(intent)
        }

        view.tv_forgetPassword.setOnClickListener {
            val intent = Intent(activity, ForgetPassword::class.java)
            startActivity(intent)
        }
        return  view
    }
    private fun logedInSuccesfully(){
        val sharedPref = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Login", true)
        editor.apply()
    }

}
