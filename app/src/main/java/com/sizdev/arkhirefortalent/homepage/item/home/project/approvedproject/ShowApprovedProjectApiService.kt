package com.sizdev.arkhirefortalent.homepage.item.home.project.approvedproject

import retrofit2.http.GET

interface ShowApprovedProjectApiService {

    @GET("projectresp?search=Approved")
    suspend fun getApprovedProjectResponse(): ShowApprovedProjectResponse
}