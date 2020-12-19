package com.sizdev.arkhirefortalent.onboarding.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentWelcomeViewPagerBinding

class WelcomeViewPagerAdapter : Fragment() {

    private lateinit var binding: FragmentWelcomeViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_view_pager, container, false)
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )
        val adapter = WelcomeAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.welcomeViewPager.adapter = adapter
        return  binding.root
    }

}