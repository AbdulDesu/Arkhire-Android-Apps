package com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject

import com.google.gson.annotations.SerializedName

data class ProjectReplyResponse(val succcess : String,val message: String, val data: List<Reply>) {

    data class Reply(
            val offeringID: String,
            val projectID: String,
            @SerializedName("hiring_status") val projectTitle: String,
            @SerializedName("reply_message") val projectDuration: String)
}