package com.sizdev.arkhirefortalent.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_on_boarding)
    }
}