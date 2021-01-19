package com.sizdev.arkhirefortalent.homepage.item.account.profile.editingprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class EditProfileViewModel: ViewModel(), CoroutineScope {
    val isSuccess = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    private lateinit var service: ArkhireApiService


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ArkhireApiService){
        this.service = service
    }

    fun updateBasicInfo(talentID : String, talentTitle: RequestBody, talentWorkTime: RequestBody, talentLocation: RequestBody, talentDesc: RequestBody, talentImage:MultipartBody.Part) {
        launch {
            isLoading.value = true
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateTalentInfo(talentID, talentTitle, talentWorkTime, talentLocation, talentDesc, talentImage)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isSuccess.value = "Failed"
                    }
                }
            }

            if (result is EditProfileResponse) {
                isLoading.value = false
                if (result.success) {
                    isSuccess.value = "Success"
                }
                else {
                    isSuccess.value = "Failed"
                }
            }
        }
    }

    fun updateBasicInfoWithoutImage(talentID : String, talentTitle: String, talentWorkTime: String, talentLocation: String, talentDesc: String, talentImage:String) {
        launch {
            isLoading.value = true
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateTalentInfoWithoutImage(talentID, talentTitle, talentWorkTime, talentLocation, talentDesc, talentImage)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isSuccess.value = "Failed"
                    }
                }
            }

            if (result is EditProfileResponse) {
                isLoading.value = false
                if (result.success) {
                    isSuccess.value = "Success"
                }
                else {
                    isSuccess.value = "Failed"
                }
            }
        }
    }

    fun updateSkill(talentID : String, skill1: String, skill2: String, skill3: String, skill4: String, skill5: String) {
        launch {
            isLoading.value = true
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateTalentSkill(talentID, skill1, skill2, skill3, skill4, skill5)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isSuccess.value = "Failed"
                    }
                }
            }

            if (result is EditProfileSkillResponse) {
                if (result.success) {
                    isSuccess.value = "Success"
                }
                else {
                    isSuccess.value = "Failed"
                }
            }
        }
    }

    fun updateGithub(talentID : String, talentGithub: String) {
        launch {
            isLoading.value = true
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateTalentGithub(talentID, talentGithub)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isSuccess.value = "Failed"
                    }
                }
            }

            if (result is EditAchivementResponse) {
                if (result.success) {
                    isLoading.value = false
                    isSuccess.value = "Success"
                }
                else {
                    isSuccess.value = "Failed"
                }
            }
        }
    }
}