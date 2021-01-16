package com.sizdev.arkhirefortalent.homepage.item.explore.contributor

interface ContributorContract {

    interface View {
        fun addListContributor(list: List<ContributorModel>)
        fun setError(error: String)
        fun hideProgressBar()
        fun showNotFound()
    }

    interface Presenter{
        fun bindToView(view: View)
        fun unbind()
        fun showContributorApi(projectID : String)
    }
}