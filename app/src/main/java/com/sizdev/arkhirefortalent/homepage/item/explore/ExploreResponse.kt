package com.sizdev.arkhireforcompany.homepage.item.explore

import com.google.gson.annotations.SerializedName

data class ExploreResponse(val success: String, val message: String, val data: List<ExploreProject>) {
    data class ExploreProject(@SerializedName("project_tittle") val projectTitle: String,
                              @SerializedName("project_desc") val projectDesc: String,
                              @SerializedName("project_sallary") val projectSallary: String,
                              @SerializedName("project_owner") val projectOwner: String,
                              @SerializedName("company_name") val projectOwnerName: String,
                              @SerializedName("company_image") val projectOwnerImage: String,
                              @SerializedName("postedAt") val postedAt: String)
}