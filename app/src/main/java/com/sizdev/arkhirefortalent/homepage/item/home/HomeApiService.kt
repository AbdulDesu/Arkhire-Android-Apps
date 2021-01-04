package com.sizdev.arkhirefortalent.homepage.item.home

import android.content.Context
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.approvedproject.ShowApprovedProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.declinedproject.ShowDeclinedProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject.ProjectReplyResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject.HighLightProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.waitingproject.ShowWaitingProjectResponse
import retrofit2.http.*

interface HomeApiService {
    
    @GET("/account/{accountID}")
    suspend fun getAccountResponse(@Path ("accountID") accountID: String): HomeResponse

    @GET("projectresp")
    suspend fun getAllProjectResponse(): ShowAllProjectResponse

    @GET("projectresp?search=Approved")
    suspend fun getApprovedProjectResponse(): ShowApprovedProjectResponse

    @GET("projectresp?search=Declined")
    suspend fun getDeclinedProjectResponse(): ShowDeclinedProjectResponse

    @GET("projectresp/newer?limit=5")
    suspend fun getNewerProjectResponse(): HighLightProjectResponse

    @GET("projectresp?search=Waiting")
    suspend fun getWaitingProjectResponse(): ShowWaitingProjectResponse

    @FormUrlEncoded
    @PUT("/talentresp/{offeringID}")
    suspend fun updateProjectByOfferingID(
            @Path("offeringID") offeringID: String,
            @Field("hiring_status") hiringStatus: String,
            @Field("reply_message") replyMsg: String) : ProjectReplyResponse
}