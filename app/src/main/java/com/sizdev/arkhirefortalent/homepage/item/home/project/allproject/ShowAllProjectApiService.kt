package com.sizdev.arkhirefortalent.homepage.item.home.project.allproject

import retrofit2.http.GET

interface ShowAllProjectApiService {

    @GET("projectresp")
    suspend fun getAllProjectResponse(): ShowAllProjectResponse
}