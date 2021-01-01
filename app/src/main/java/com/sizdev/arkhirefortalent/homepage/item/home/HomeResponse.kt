package com.sizdev.arkhirefortalent.homepage.item.home

import com.google.gson.annotations.SerializedName


data class HomeResponse (val success: String, val message: String, val data: List<HomeResponse.talent>) {
    data class talent(@SerializedName("accountID") val offeringID: String,
                      @SerializedName("talentID") val projectID: String,
                      @SerializedName("account_name") val accountName: String)
}