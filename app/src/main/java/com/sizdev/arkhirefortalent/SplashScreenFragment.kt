package com.sizdev.arkhirefortalent

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sizdev.arkhirefortalent.homepage.HomeActivity

class SplashScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            if(onBoardingFinish()){
                    if (logedInSuccesfully()){
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    else{
                        findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                    }
            }
            else{
                findNavController().navigate(R.id.action_splashScreenFragment_to_welcomeViewPager5)
            }
        }, 3000)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun onBoardingFinish(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Complete", false)
    }

    private fun logedInSuccesfully(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Login", false)
    }

}