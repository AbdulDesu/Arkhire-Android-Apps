package com.sizdev.arkhirefortalent.homepage.item.home.project

interface ProjectContract {

    interface View {
        fun addProjectList(list: List<ProjectModel>)
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
        fun getAllProject(accountID: String)
        fun getHighlightProject(accountID: String)
        fun getApprovedProject(accountID: String)
        fun getDeclinedProject(accountID: String)
        fun getWaitingProject(accountID: String)
    }


}