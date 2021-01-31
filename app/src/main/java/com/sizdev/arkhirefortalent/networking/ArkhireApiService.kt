package com.sizdev.arkhirefortalent.networking

import com.sizdev.arkhirefortalent.homepage.item.explore.ExploreResponse
import com.sizdev.arkhirefortalent.administration.email.ResetEmailResponse
import com.sizdev.arkhirefortalent.administration.login.LoginResponse
import com.sizdev.arkhirefortalent.administration.password.ResetPasswordResponse
import com.sizdev.arkhirefortalent.administration.register.RegisterResponse
import com.sizdev.arkhirefortalent.homepage.item.account.AccountResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.curiculumvitae.CurriculumVitaeResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditAchivementResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditProfileResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile.EditProfileSkillResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.create.CreatePortfolioResponse
import com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience.WorkExperienceResponse
import com.sizdev.arkhirefortalent.homepage.item.company.SearchCompanyResponse
import com.sizdev.arkhirefortalent.homepage.item.explore.contributor.ContributorResponse
import com.sizdev.arkhirefortalent.homepage.item.home.HomeResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectResponse
import com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject.ProjectReplyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    // Account Service
    @GET("/talent/talentaccount/{accountID}")
    suspend fun getAccountDataByHolderResponse(@Path("accountID") accountID: String): AccountResponse

    @GET("/talent/{talentID}")
    suspend fun getTalentProfileResponse(@Path("talentID") talentID: String): AccountResponse

    @FormUrlEncoded
    @PUT("/account/{accountID}")
    suspend fun updateEmailResponse(@Path("accountID") accountID: String,
                                    @Field("account_email") accountEmail: String): ResetEmailResponse

    @FormUrlEncoded
    @PUT("/account/password/{accountID}")
    suspend fun updatePasswordResponse(@Path("accountID") accountID: String,
                                       @Field("password") newPassword: String): ResetPasswordResponse

    // Home Service
    @GET("/talent/talentaccount/{accountID}")
    suspend fun getAccountResponse(@Path("accountID") accountID: String): HomeResponse

    // Project Service
    @GET("contributor/participator/{accountID}")
    suspend fun getAllProjectResponse(@Path("accountID") accountID: String): ProjectResponse

    @GET("contributor/participator/{accountID}?search=Approved")
    suspend fun getApprovedProjectResponse(@Path("accountID") accountID: String): ProjectResponse

    @GET("contributor/participator/{accountID}?search=Declined")
    suspend fun getDeclinedProjectResponse(@Path("accountID") accountID: String): ProjectResponse

    @GET("contributor/participator/{accountID}?search=Waiting")
    suspend fun getWaitingProjectResponse(@Path("accountID") accountID: String): ProjectResponse

    @GET("projectresp/highlight/{accountID}")
    suspend fun getNewerProjectResponse(@Path("accountID") accountID: String): ProjectResponse


    @FormUrlEncoded
    @PUT("talentresp/{offeringID}")
    suspend fun updateProjectByOfferingID(@Path("offeringID") offeringID: String,
                                          @Field("hiring_status") hiringStatus: String,
                                          @Field("reply_message") replyMsg: String) : ProjectReplyResponse

    // Search Company Service
    @GET("company")
    suspend fun getAllCompany(): SearchCompanyResponse

    @GET("company/filter/name")
    suspend fun searchCompany(@Query("search") companyName:String): SearchCompanyResponse

    // Explore Service
    @GET("project")
    suspend fun startExplore() : ExploreResponse

    @GET("project")
    suspend fun searchExplore(@Query("search") projectTitle: String) : ExploreResponse

    @GET("contributor/room/{projectID}")
    suspend fun showContributor(@Path("projectID") projectId: String) : ContributorResponse

    // Profile Service
    @Multipart
    @PUT("/talent/{talentID}")
    suspend fun updateTalentInfo(
        @Path("talentID") talentID: String,
        @Part("talent_tittle") talentTitle: RequestBody,
        @Part("talent_time") talentTime: RequestBody,
        @Part("talent_city") talentCity: RequestBody,
        @Part("talent_profile") talentDesc: RequestBody,
        @Part talentImage: MultipartBody.Part) : EditProfileResponse

    @FormUrlEncoded
    @PUT("/talent/text/{talentID}")
    suspend fun updateTalentInfoWithoutImage(
            @Path("talentID") talentID: String,
            @Field("talent_tittle") talentTitle: String,
            @Field("talent_time") talentTime: String,
            @Field("talent_city") talentCity: String,
            @Field("talent_profile") talentDesc: String,
            @Field("talent_image") talentImage: String) : EditProfileResponse

    @FormUrlEncoded
    @PUT("talentskill/{talentID}")
    suspend fun updateTalentSkill(
        @Path("talentID") talentID: String,
        @Field("skill_1") talentSkill1: String,
        @Field("skill_2") talentSkill2: String,
        @Field("skill_3") talentSkill3: String,
        @Field("skill_4") talentSkill4: String,
        @Field("skill_5") talentSkill5: String) : EditProfileSkillResponse

    @FormUrlEncoded
    @PUT("/achivement/github/{talentID}")
    suspend fun updateTalentGithub(
        @Path("talentID") talentID: String,
        @Field("talent_github") talentGithub: String) : EditAchivementResponse

    @Multipart
    @PUT("/achivement/{talentID}")
    suspend fun updateCurriculumVitae(@Path("talentID") talentID: String,
                                      @Part cvImage: MultipartBody.Part ) : CurriculumVitaeResponse

    // Portfolio Service
    @GET("portfolio/owner/{accountID}")
    suspend fun getPortfolio(@Path("accountID") accountID: String): PortfolioResponse

    @GET("portfolio/{portfolioID}")
    suspend fun getPortfolioByID(@Path("portfolioID") portfolioID: String): PortfolioResponse

    @Multipart
    @POST("portfolio/createportfolio")
    suspend fun createPortfolio(@Part("portfolio_owner") portfolioOwner: RequestBody,
                                @Part("portfolio_title") portfolioTitle: RequestBody,
                                @Part("portfolio_desc") portfolioDesc: RequestBody,
                                @Part("portfolio_repository") portfolioRepository: RequestBody,
                                @Part portfolioImage: MultipartBody.Part) : CreatePortfolioResponse

    @DELETE("portfolio/{portfolioID}")
    suspend fun deletePortfolio(@Path("portfolioID") portfolioID : String) : PortfolioResponse

    // Work Experience Service
    @GET("talentexperience/owner/{accountID}")
    suspend fun getWorkExperiences(@Path("accountID") accountID: String): WorkExperienceResponse

    @FormUrlEncoded
    @POST("talentexperience/postexperience")
    suspend fun addWorkExperiences(@Field("experience_owner") experienceOwner: String,
                                   @Field("experience_title") experienceTitle: String,
                                   @Field("experience_source") experienceSource: String,
                                   @Field("experience_start") experienceStart: String,
                                   @Field("experience_end") experienceEnd: String,
                                   @Field("experience_desc") experienceDesc: String) : WorkExperienceResponse

    @DELETE("talentexperience/{experienceID}")
    suspend fun deleteWorkExperience(@Path("experienceID") experienceID: String) : WorkExperienceResponse

}