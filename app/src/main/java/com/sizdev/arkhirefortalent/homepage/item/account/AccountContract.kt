package com.sizdev.arkhirefortalent.homepage.item.account

interface AccountContract {

    interface View {
        fun setAccountData(AccountName: String?, AccountTitle: String?, accountImage: String?, talentID: String?)
        fun setRefreshManager()
        fun setService()
        fun setError(error: String)
        fun getCurrentLoginData()
        fun showProgressBar()
        fun hideProgressBar()
        fun showSessionExpired()
    }

    interface Presenter{
        fun bindToView(view: View)
        fun unbind()
        fun getAccountData(accountID: String)
    }

}