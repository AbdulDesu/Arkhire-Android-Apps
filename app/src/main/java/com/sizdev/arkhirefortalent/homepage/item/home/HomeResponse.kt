package com.sizdev.arkhirefortalent.homepage.item.home

import com.google.gson.annotations.SerializedName


data class HomeResponse (val success: Boolean, val message: String, val data: List<Talent>) {
    data class Talent(@SerializedName("account_name") val accountName: String,
                      @SerializedName("talentID") val talentID: String,
                      @SerializedName("talent_tittle") val talentTitle: String?)
}