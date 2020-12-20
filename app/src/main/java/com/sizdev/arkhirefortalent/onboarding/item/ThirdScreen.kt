package com.sizdev.arkhirefortalent.onboarding.item

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentThirdScreenBinding

class ThirdScreen : Fragment() {

    private lateinit var binding : FragmentThirdScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third_screen, container, false)
        binding.tvStart.setOnClickListener {
            findNavController().navigate(R.id.action_splashScreenFragment2_to_loginActivity)
            onBoardingFinish()
        }
        return binding.root
    }
    private fun onBoardingFinish(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Complete", true)
        editor.apply()
    }
}