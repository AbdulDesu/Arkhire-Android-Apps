package com.sizdev.arkhirefortalent.homepage.item.home

import com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject.HighLightProjectModel

interface HomeContract {

    interface View {
        fun addHighlightProject(list: List<HighLightProjectModel>)
        fun setGreeting(name: String)
        fun setError(error: String)
        fun hideProgressBar()
    }

    interface Presenter{
        fun bindToView(view: View)
        fun unbind()
        fun getAccountName(accountID: String)
        fun getHighlightedProject(accountID : String)
    }
}