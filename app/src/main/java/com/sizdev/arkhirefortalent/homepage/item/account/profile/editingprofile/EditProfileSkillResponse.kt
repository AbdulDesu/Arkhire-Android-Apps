package com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile

import com.google.gson.annotations.SerializedName

data class EditProfileSkillResponse(val success: Boolean, val message: String, val data: UpdateSkill?) {
    data class UpdateSkill(
        @SerializedName("skill_1") val talentSkill1: String?,
        @SerializedName("skill_2") val talentSkill2: String?,
        @SerializedName("skill_3") val talentSkill3: String?,
        @SerializedName("skill_4") val talentSkill4: String?,
        @SerializedName("skill_5") val talentSkill5: String?
    )
}
