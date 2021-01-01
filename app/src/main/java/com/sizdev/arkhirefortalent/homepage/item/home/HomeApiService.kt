package com.sizdev.arkhirefortalent.homepage.item.home

import android.content.Context
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApiService {
    
    @GET("/talent/filter/name?search={account_name}")
    suspend fun getTalentResponse(@Path ("account_name") accountName: String): HomeResponse
}