package com.sizdev.arkhireforcompany.homepage.item.explore

import com.google.gson.annotations.SerializedName

data class ExploreResponse(val success: Boolean, val message: String, val data: List<ExploreProject>) {
    data class ExploreProject(
            @SerializedName("projectID") val projectID: String,
            @SerializedName("project_tittle") val projectTitle: String,
            @SerializedName("project_duration") val projectDuration: String,
            @SerializedName("project_desc") val projectDesc: String,
            @SerializedName("project_sallary") val projectSalary: String,
            @SerializedName("project_image") val projectImage: String,
            @SerializedName("project_owner") val companyID: String,
            @SerializedName("company_name") val companyName: String,
            @SerializedName("company_image") val companyImage: String,
            @SerializedName("postedAt") val postedAt: String)
}