package com.sizdev.arkhirefortalent.homepage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityHomeBinding
import com.sizdev.arkhirefortalent.homepage.item.AccountFragment
import com.sizdev.arkhirefortalent.homepage.item.ChatFragment
import com.sizdev.arkhirefortalent.homepage.item.HomeFragment
import com.sizdev.arkhirefortalent.homepage.item.SearchCompanyFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    lateinit var accountFragment: AccountFragment
    lateinit var homeFragment: HomeFragment
    lateinit var searchFragment: SearchCompanyFragment
    lateinit var chatFragment: ChatFragment

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
}