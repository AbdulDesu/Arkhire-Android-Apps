package com.sizdev.arkhirefortalent.homepage.item.company

import com.google.gson.annotations.SerializedName

data class SearchCompanyResponse(val success: String, val message: String, val data: List<Project>) {
    data class Project(@SerializedName("companyID") val companyID: String,
                       @SerializedName("accountID") val accountID: String,
                       @SerializedName("company_name") val companyName: String,
                       @SerializedName("company_position") val companyPosition: String,
                       @SerializedName("company_latitude") val companyLatitude: String,
                       @SerializedName("company_longitude") val companyLongitude: String,
                       @SerializedName("company_type") val companyType: String,
                       @SerializedName("company_detail") val companyDesc: String,
                       @SerializedName("company_linkedin") val companyLinkedin: String,
                       @SerializedName("company_instagram") val companyInstagram: String,
                       @SerializedName("company_facebook") val companyFacebook: String,
                       @SerializedName("company_image") val companyImage: String,
                       @SerializedName("updatedAt") val updatedAt: String)
}