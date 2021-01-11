package com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject

import com.google.gson.annotations.SerializedName

data class HighLightProjectResponse(val success: String, val message: String, val data: List<HighLightProjectResponse.NewProject>) {
    data class NewProject(@SerializedName("offeringID") val offeringID: String,
                          @SerializedName("projectID") val projectID: String,
                          @SerializedName("project_tittle") val projectTitle: String,
                          @SerializedName("project_duration") val projectDuration: String,
                          @SerializedName("project_desc") val projectDesc: String,
                          @SerializedName("project_sallary") val projectSallary: String,
                          @SerializedName("project_owner") val projectOwner: String,
                          @SerializedName("company_name") val projectOwnerName: String,
                          @SerializedName("company_image") val projectOwnerImage: String,
                          @SerializedName("hiring_status") val hiringStatus: String,
                          @SerializedName("reply_message") val replyMsg: String,
                          @SerializedName("repliedAt") val repliedAt: String)
}