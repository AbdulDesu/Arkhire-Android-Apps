package com.sizdev.arkhirefortalent.homepage

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityHomeBinding
import com.sizdev.arkhirefortalent.homepage.item.account.AccountFragment
import com.sizdev.arkhirefortalent.homepage.item.chat.ChatFragment
import com.sizdev.arkhirefortalent.homepage.item.home.HomeFragment
import com.sizdev.arkhirefortalent.homepage.item.company.SearchCompanyFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    lateinit var accountFragment: AccountFragment
    lateinit var homeFragment: HomeFragment
    lateinit var searchFragment: SearchCompanyFragment
    lateinit var chatFragment: ChatFragment

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        //Launch Home Fragment At First
        binding.homeNavigationBar.setItemSelected(R.id.home)
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.homeViewer, homeFragment)
            .commit()

        binding.homeNavigationBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.homeViewer, homeFragment)
                        .commit()
                }

                R.id.searchCompany -> {
                    searchFragment = SearchCompanyFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.homeViewer, searchFragment)
                        .commit()
                }

                R.id.chat -> {
                    chatFragment = ChatFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.homeViewer, chatFragment)
                        .commit()
                }

                R.id.account -> {
                    accountFragment = AccountFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.homeViewer, accountFragment)
                        .commit()
                    }
                }
            true
            }
        }

    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tap back again to exit from apps", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}