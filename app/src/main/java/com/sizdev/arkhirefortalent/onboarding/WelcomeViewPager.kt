package com.sizdev.arkhirefortalent.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.onboarding.item.FirstScreen
import com.sizdev.arkhirefortalent.onboarding.item.IntroFragment
import com.sizdev.arkhirefortalent.onboarding.item.SecondScreen
import com.sizdev.arkhirefortalent.onboarding.item.ThirdScreen
import kotlinx.android.synthetic.main.fragment_welcome_view_pager.*
import kotlinx.android.synthetic.main.fragment_welcome_view_pager.view.*

class WelcomeViewPager : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_welcome_view_pager, container, false)
        val fragmentList = arrayListOf<Fragment>(
                IntroFragment(),
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = AdapterWelcome(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.welcomeViewPager.adapter = adapter
        return  view
    }

}