package com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface EditProfileAuthService {
    @Multipart
    @PUT("/talent/{talentID}")
    suspend fun updateTalent(
            @Path("talentID") talentID: String,
            @Part("talent_tittle") talentTitle: String,
            @Part("talent_time") talentTime: String,
            @Part("talent_city") talentCity: String,
            @Part("talent_profile") talentDesc: String,
            @Part("talent_image") talentImage: String) : EditProfileResponse
}