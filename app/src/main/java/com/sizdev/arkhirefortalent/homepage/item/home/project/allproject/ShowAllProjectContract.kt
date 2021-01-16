package com.sizdev.arkhirefortalent.homepage.item.home.project.allproject

interface ShowAllProjectContract {

    interface View {
        fun addAllProjectList(list: List<ShowAllProjectModel>)
        fun setRefreshManager()
        fun setService()
        fun setError(error: String)
        fun getCurrentLoginData()
        fun showProgressBar()
        fun setRecyclerView()
        fun hideProgressBar()
        fun setActionBar()
        fun showSessionExpired()
    }

    interface Presenter{
        fun bindToView(view: View)
        fun unbind()
        fun getCurrentProject(accountID: String)
    }

}