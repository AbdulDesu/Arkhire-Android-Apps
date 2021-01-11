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
    private lateinit var service: ArkhireApiService


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ArkhireApiService){
        this.service = service
    }

    fun updateBasicInfo(talentID : String, talentTitle: RequestBody, talentWorkTime: RequestBody, talentLocation: RequestBody, talentDesc: RequestBody, talentImage:MultipartBody.Part) {
        launch {
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

    fun updateAchivement(talentID : String, talentGithub: RequestBody, talentCv: MultipartBody.Part) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateTalentAchivement(talentID, talentGithub, talentCv)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isSuccess.value = "Failed"
                    }
                }
            }

            if (result is EditAchivementResponse) {
                if (result.success) {
                    isSuccess.value = "Success"
                }
                else {
                    isSuccess.value = "Failed"
                }
            }
        }
    }
}