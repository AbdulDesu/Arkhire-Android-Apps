package com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.create

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioResponse
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class CreatePortfolioViewModel: ViewModel(), CoroutineScope {

    val isSuccess = MutableLiveData<String>()
    private lateinit var service: ArkhireApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ArkhireApiService){
        this.service = service
    }

    fun uploadPortfolio(portfolioOwner: RequestBody, portfolioTitle: RequestBody, portfolioDesc: RequestBody, portfolioRepository: RequestBody, portfolioImage: MultipartBody.Part) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.createPortfolio(portfolioOwner, portfolioTitle, portfolioDesc, portfolioRepository, portfolioImage)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isSuccess.value = "Failed"
                    }
                }
            }

            if (result is CreatePortfolioResponse) {
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