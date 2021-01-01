package com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProjectReplyAuthService {
    @FormUrlEncoded
    @PUT("/talentresp/{id}")
    suspend fun updateProjectByOfferingID(
            @Path("id") offeringID: String,
            @Field("hiring_status") hiringStatus: String,
            @Field("reply_message") replyMsg: String) : ProjectReplyResponse
}