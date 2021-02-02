package com.sizdev.arkhirefortalent.homepage.item.home

import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectModel
interface HomeContract {

    interface View {
        fun addHighlightProject(list: List<ProjectModel>)
        fun setGreeting(name: String, talentID: String, talentTitle: String)
        fun setError(error: String)
        fun hideProgressBar()
        fun alertMustCompleteData()
    }

    interface Presenter{
        fun bindToView(view: View)
        fun unbind()
        fun getAccountName(accountID: String)
        fun getHighlightedProject(accountID : String)
    }
}