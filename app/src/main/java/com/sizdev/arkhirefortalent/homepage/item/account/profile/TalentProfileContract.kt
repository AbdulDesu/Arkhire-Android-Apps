package com.sizdev.arkhirefortalent.homepage.item.account.profile

interface TalentProfileContract {

    interface View {
        fun setService()
        fun showProgressBar()
        fun getSavedData()
        fun setTalentData(talentID: String?,
                          accountName: String?,
                          accountEmail: String?,
                          accountPhone: String?,
                          talentTitle: String?,
                          talentTime: String?,
                          talentCity: String?,
                          talentDesc: String?,
                          talentImage: String?,
                          talentGithub: String?,
                          talentCv: String?,
                          talentSkill1: String?,
                          talentSkill2: String?,
                          talentSkill3: String?,
                          talentSkill4: String?,
                          talentSkill5: String?)
        fun setRefreshManager()
        fun setError(error: String)
        fun hideProgressBar()
        fun sessionExpiredAlert()
        fun setProfileTab()
        fun setFloatingButton()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun getTalentData(talentID: String)
    }

}