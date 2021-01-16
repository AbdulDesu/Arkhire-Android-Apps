package com.sizdev.arkhirefortalent.homepage.item.explore.contributor

import com.google.gson.annotations.SerializedName

data class ContributorResponse(val success: Boolean, val message: String, val data: List<ContributorResponse.Contributor>){
    data class Contributor(@SerializedName("account_name") val accountName: String,
                           @SerializedName("talent_tittle") val accountTitle: String,
                           @SerializedName("talent_image") val talentImage: String)
}
