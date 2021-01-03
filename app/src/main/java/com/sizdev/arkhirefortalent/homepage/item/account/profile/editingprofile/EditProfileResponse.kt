package com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(val success: Boolean, val message: String, val data: UpdateData?) {
    data class UpdateData(
            @SerializedName("talent_tittle") val talentTitle: String?,
            @SerializedName("talent_time") val talentTime: String?,
            @SerializedName("talent_city") val talentLocation: String?,
            @SerializedName("talent_profile") val talentDesc: String?,
            @SerializedName("talent_image") val talentImage: String?
    )
}