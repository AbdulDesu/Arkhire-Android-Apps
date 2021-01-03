package com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject

import retrofit2.http.GET

interface HighLightProjectApiService {

    @GET("projectresp/newer")
    suspend fun getNewerProjectResponse(): HighLightProjectResponse
}