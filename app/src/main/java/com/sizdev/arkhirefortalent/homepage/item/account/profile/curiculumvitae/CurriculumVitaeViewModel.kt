package com.sizdev.arkhirefortalent.homepage.item.account.profile.curiculumvitae

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class CurriculumVitaeViewModel: ViewModel(), CoroutineScope {
    val isSuccess = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    private lateinit var service: ArkhireApiService


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ArkhireApiService){
        this.service = service
    }

    fun updateCurriculumVitae(talentID : String, cvImage:MultipartBody.Part) {
        launch {
            isLoading.value = true
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateCurriculumVitae(talentID, cvImage)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isSuccess.value = "Failed"
                    }
                }
            }

            if (result is CurriculumVitaeResponse) {
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
}