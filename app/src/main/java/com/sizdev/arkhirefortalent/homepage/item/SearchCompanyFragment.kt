package com.sizdev.arkhirefortalent.homepage.item

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.FragmentSearchCompanyBinding

class SearchCompanyFragment : Fragment() {

    private lateinit var binding: FragmentSearchCompanyBinding
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_company, container, false)

        val sharedPrefData = requireActivity().getSharedPreferences(
            "fullData",
            Context.MODE_PRIVATE
        )
        val sharedPrefProfileData = requireActivity().getSharedPreferences(
            "profileData",
            Context.MODE_PRIVATE
        )

        val savedName = sharedPrefData.getString("fullName", null)
        val talentTitle = sharedPrefProfileData.getString("talentTitle", null)

        binding.tvSearchTalentName.text = savedName
        binding.tvSearchTalentTitle.text = talentTitle

        if(savedName != null && talentTitle != null){
            binding.talent.setVisibility(View.VISIBLE)
            binding.missingContent.setVisibility(View.INVISIBLE)
        }
        else {
            binding.talent.setVisibility(View.INVISIBLE)
        }

        return binding.root
    }

}