package com.sizdev.arkhirefortalent.homepage.item

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sizdev.arkhirefortalent.R
import kotlinx.android.synthetic.main.fragment_search_company.*
import kotlinx.android.synthetic.main.fragment_search_company.view.*
import kotlinx.android.synthetic.main.hiring_confirmation.view.*

class SearchFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_search_company, container, false)

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

        view.tv_searchTalentName.text = savedName
        view.tv_searchTalentTitle.text =talentTitle

        if(savedName != null && talentTitle != null){
            view.talent.setVisibility(View.VISIBLE)
            view.missingContent.setVisibility(View.INVISIBLE)
        }
        else {
            view.talent.setVisibility(View.INVISIBLE)
        }

        view.talent.setOnClickListener {
            val intent = Intent(activity, DemoHiringProgress::class.java)
            startActivity(intent)

        }
        return view
    }

}