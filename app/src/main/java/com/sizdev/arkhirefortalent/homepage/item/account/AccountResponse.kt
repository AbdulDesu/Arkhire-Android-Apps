package com.sizdev.arkhirefortalent.homepage.item.account

import com.google.gson.annotations.SerializedName

data class AccountResponse(val success: String, val message: String, val data: List<AccountData>) {

    data class AccountData(
            val talentID : String?,
            val accountID: String?,
            @SerializedName("account_name")  val accountName: String?,
            @SerializedName("talent_tittle") val talentTitle: String?,
            @SerializedName("talent_image")val talentImage: String?
    )
}