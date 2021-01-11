package com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience.WorkExperienceResponse
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddWorkExperienceViewModel: ViewModel(), CoroutineScope {

    val isSuccess = MutableLiveData<String>()
    private lateinit var service: ArkhireApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ArkhireApiService){
        this.service = service
    }

    fun addWorkExperience(experienceOwner: String, experienceTitle: String, experienceSource: String, experienceStart: String, experienceEnd: String, experienceDesc: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.addWorkExperiences(experienceOwner, experienceTitle, experienceSource, experienceStart, experienceEnd, experienceDesc)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isSuccess.value = "Failed"
                    }
                }
            }

            if (result is WorkExperienceResponse) {
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