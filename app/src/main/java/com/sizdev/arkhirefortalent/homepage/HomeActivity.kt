package com.sizdev.arkhirefortalent.homepage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sizdev.arkhirefortalent.LoginActivity
import com.sizdev.arkhirefortalent.LoginFragment
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.homepage.item.AccountFragment
import com.sizdev.arkhirefortalent.homepage.item.ChatFragment
import com.sizdev.arkhirefortalent.homepage.item.HomeFragment
import com.sizdev.arkhirefortalent.homepage.item.SearchFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_account.*

class HomeActivity : AppCompatActivity() {
    lateinit var accountFragment: AccountFragment
    lateinit var homeFragment: HomeFragment
    lateinit var searchFragment: SearchFragment
    lateinit var chatFragment: ChatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            val message = bundle.getString("fullName") // 1

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        }

        //Launch Home Fragment At First
        homeNavigationBar.setItemSelected(R.id.home)
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.homeViewer, homeFragment)
            .commit()

        homeNavigationBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.homeViewer, homeFragment)
                        .commit()
                }

                R.id.searchCompany -> {
                    searchFragment = SearchFragment()
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
}