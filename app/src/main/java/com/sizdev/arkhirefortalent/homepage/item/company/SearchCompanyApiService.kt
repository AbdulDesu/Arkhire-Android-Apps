package com.sizdev.arkhirefortalent.homepage.item.company

import retrofit2.http.GET

interface SearchCompanyApiService {

    @GET("company")
    suspend fun getAllCompany(): SearchCompanyResponse

}