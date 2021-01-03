package com.sizdev.arkhirefortalent.homepage.item.home

import android.content.Context
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApiService {
    
    @GET("/account/{accountID}")
    suspend fun getAccountResponse(@Path ("accountID") accountID: String): HomeResponse
}