package com.sizdev.arkhirefortalent.homepage.item.company


interface SearchCompanyContract {
    interface View {
        fun addCompanyList(list: List<SearchCompanyModel>)
        fun setError(error: String)
        fun hideProgressBar()
        fun showNotFound()
    }

    interface Presenter{
        fun bindToView(view: View)
        fun unbind()
        fun getCompanyList()
        fun searchCompany(search: String)
    }
}