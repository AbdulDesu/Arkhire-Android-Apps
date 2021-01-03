package com.sizdev.arkhirefortalent.homepage.item.account

import retrofit2.http.GET
import retrofit2.http.Query

interface AccountApiService {
    @GET("/talent/filter/name")
    suspend fun getAccountDataByNameResponse(@Query("search") accountName : String): AccountResponse
}