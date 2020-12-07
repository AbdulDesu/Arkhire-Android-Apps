package com.sizdev.arkhirefortalent.onboarding.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sizdev.arkhirefortalent.R
import kotlinx.android.synthetic.main.fragment_welcome_view_pager.*

class FirstScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first_screen, container, false)

        welcomeViewPager?.currentItem = 1
        return view
    }

}