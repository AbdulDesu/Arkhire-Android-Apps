package com.sizdev.arkhirefortalent.homepage.item.account.profile

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TalentProfileAuthService {
    @GET("/talent/filter/name")
    suspend fun getTalentProfileByNameResponse(@Query("search") accountName : String): TalentProfileResponse
}