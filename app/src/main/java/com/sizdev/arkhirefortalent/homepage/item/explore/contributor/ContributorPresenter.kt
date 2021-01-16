package com.sizdev.arkhirefortalent.homepage.item.explore.contributor

import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ContributorPresenter (private val coroutineScope: CoroutineScope,
                            private val service: ArkhireApiService?) : ContributorContract.Presenter {

    private var view: ContributorContract.View? = null


    override fun bindToView(view: ContributorContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun showContributorApi(projectID: String) {
        coroutineScope.launch {
            coroutineScope.launch {
                val result = withContext(Dispatchers.IO) {
                    try {
                        service?.showContributor(projectID)
                    } catch (e: HttpException) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            when {
                                e.code() == 403 -> {
                                    view?.setError("Session Expired !")
                                }

                                e.code() == 404 -> {
                                    view?.setError("Not Found")
                                }


                                else -> {
                                    view?.setError("Unknown Error, Please Try Again Later !")
                                }
                            }
                        }
                    }
                }


                if (result is ContributorResponse) {
                    if(result.success){
                        val list = result.data?.map {
                            ContributorModel(it.accountName, it.accountTitle, it.talentImage)
                        }
                        view?.hideProgressBar()
                        view?.addListContributor(list)
                    }

                    else {
                        view?.setError(result.message)
                    }
                }

                else {
                    view?.hideProgressBar()
                    view?.showNotFound()
                }
            }
        }
    }
}