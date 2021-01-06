package com.sizdev.arkhirefortalent.networking

import com.sizdev.arkhirefortalent.administration.login.LoginResponse
import com.sizdev.arkhirefortalent.administration.register.RegisterResponse
import com.sizdev.arkhirefortalent.homepage.item.account.AccountResponse
import com.sizdev.arkhirefortalent.homepage.item.company.SearchCompanyResponse
import com.sizdev.arkhirefortalent.homepage.item.home.HomeResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.approvedproject.ShowApprovedProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.declinedproject.ShowDeclinedProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject.ProjectReplyResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject.HighLightProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.waitingproject.ShowWaitingProjectResponse
import retrofit2.http.*

interface ArkhireApiService {

    // Login Service
    @FormUrlEncoded
    @POST("/account/login")
    suspend fun loginRequest(@Field("email") email: String,
                             @Field("password") password: String ) : LoginResponse

    // Register Service
    @FormUrlEncoded
    @POST("/account/register")
    suspend fun registerRequest(@Field("account_name") acName: String,
                                @Field("account_email") acEmail: String,
                                @Field("account_phone") acPhone: String,
                                @Field("password") password: String,
                                @Field("privilege") privilege: Int ) : RegisterResponse

    // Home Service
    @GET("/account/{accountID}")
    suspend fun getAccountResponse(@Path("accountID") accountID: String): HomeResponse

    // Project Service
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
    suspend fun updateProjectByOfferingID(@Path("offeringID") offeringID: String,
                                          @Field("hiring_status") hiringStatus: String,
                                          @Field("reply_message") replyMsg: String) : ProjectReplyResponse

    // Search Company Service
    @GET("company")
    suspend fun getAllCompany(): SearchCompanyResponse

    // Account Service
    @GET("/talent/filter/name")
    suspend fun getAccountDataByNameResponse(@Query("search") accountName : String): AccountResponse
}