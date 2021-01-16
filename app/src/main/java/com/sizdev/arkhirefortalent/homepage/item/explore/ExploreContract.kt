package com.sizdev.arkhirefortalent.homepage.item.explore


interface ExploreContract {
    interface View {
        fun showExploreList(list: List<ExploreModel>)
        fun setError(error: String)
        fun hideProgressBar()
        fun showNotFound()
    }

    interface Presenter{
        fun bindToView(view: View)
        fun unbind()
        fun exploreProject()
        fun searchProject(search: String)
    }
}