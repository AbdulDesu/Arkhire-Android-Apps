package com.sizdev.arkhirefortalent.homepage.item.home.project.declinedproject

import retrofit2.http.GET

interface ShowDeclinedProjectApiService {

    @GET("projectresp?search=Declined")
    suspend fun getDeclinedProjectResponse(): ShowDeclinedProjectResponse
}