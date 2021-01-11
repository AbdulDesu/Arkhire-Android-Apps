package com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile

import com.google.gson.annotations.SerializedName

data class EditAchivementResponse (val success: Boolean, val message: String, val data: UpdateAchivement?) {
    data class UpdateAchivement(
        @SerializedName("achivementID") val achivementID: String?,
        @SerializedName("talentID") val talentID: String?,
        @SerializedName("talent_github") val talentTime: String?,
        @SerializedName("talent_cv") val talentLocation: String?
    )
}