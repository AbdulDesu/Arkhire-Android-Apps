package com.sizdev.arkhirefortalent.administration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)
    }
}