package com.sizdev.arkhirefortalent.homepage.item.account.profile

interface TalentProfileContract {

    interface View {
        fun setError(error: String)
        fun showProgressBar()
        fun setTalentData(talentName: String, talentTitle: String)
        fun hideProgressBar()
        fun sessionExpiredAlert()
        fun setProfileTab()
        fun setFloatingButton()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun getTalentByID(talentID: String)
    }

}