package com.sizdev.arkhirefortalent.onboarding.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentFirstScreenBinding
import kotlinx.android.synthetic.main.fragment_welcome_view_pager.*

class FirstScreen : Fragment() {

    private lateinit var binding: FragmentFirstScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_first_screen, container, false)

        return binding.root
    }

}