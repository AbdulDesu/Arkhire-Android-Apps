package com.sizdev.arkhirefortalent.onboarding.item

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sizdev.arkhirefortalent.R
import kotlinx.android.synthetic.main.fragment_intro.*
import kotlinx.android.synthetic.main.fragment_intro.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_intro, container, false)


        view.tv_intro.typeWrite(this@IntroFragment, "Hi", 90L)

        Handler().postDelayed({
            view.tv_intro.typeWrite(this@IntroFragment, "Let Me Show\nWhat Arkhire Do For You", 90L)
        },2000)

        return view
    }


}

fun TextView.typeWrite(lifecycleOwner: LifecycleOwner, text: String, intervalMs: Long) {
    this@typeWrite.text = ""
    lifecycleOwner.lifecycleScope.launch {
        repeat(text.length) {
            delay(intervalMs)
            this@typeWrite.text = text.take(it + 1)
        }
    }
}
