package com.sizdev.arkhirefortalent.homepage.item.company

import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchCompanyPresenter ( private val coroutineScope: CoroutineScope, private val service: ArkhireApiService?) : SearchCompanyContract.Presenter {

    private var view: SearchCompanyContract.View? = null

    override fun bindToView(view: SearchCompanyContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun getCompanyList() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getAllCompany()
                } catch (e: HttpException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        when{
                            e.code() == 403 -> {
                                view?.setError("Session Expired !")
                            }

                            else -> {
                                view?.setError("Unknown Error, Please Try Again Later !")
                            }

                        }
                    }
                }
            }

            if (result is SearchCompanyResponse) {
                if (result.success){
                    val list = result.data?.map{
                        SearchCompanyModel(it.companyID,
                                it.accountID,
                                it.companyName,
                                it.companyPosition,
                                it.companyLocation,
                                it.companyLatitude,
                                it.companyLongitude,
                                it.companyType,
                                it.companyDesc,
                                it.companyLinkedin,
                                it.companyInstagram,
                                it.companyFacebook,
                                it.companyImage,
                                it.updatedAt)
                    }
                    view?.hideProgressBar()
                    view?.addCompanyList(list)
                }
                else {
                    view?.hideProgressBar()
                    view?.setError(result.message)
                }
            }
        }
    }

    override fun searchCompany(search: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.searchCompany(search)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is SearchCompanyResponse) {
                if (result.success){
                val list = result.data?.map {
                    SearchCompanyModel(
                            it.companyID,
                            it.accountID,
                            it.companyName,
                            it.companyPosition,
                            it.companyLocation,
                            it.companyLatitude,
                            it.companyLongitude,
                            it.companyType,
                            it.companyDesc,
                            it.companyLinkedin,
                            it.companyInstagram,
                            it.companyFacebook,
                            it.companyImage,
                            it.updatedAt)
                    }
                    view?.addCompanyList(list)
                }
            }

            else {
                view?.showNotFound()
            }
        }
    }
}