package com.sizdev.arkhirefortalent.administration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
    }
}