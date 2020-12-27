package com.sizdev.arkhirefortalent.homepage.item.home.project.waitingproject

import retrofit2.http.GET

interface ShowWaitingProjectApiService {

    @GET("projectresp?search=Waiting")
    suspend fun getWaitingProjectResponse(): ShowWaitingProjectResponse
}