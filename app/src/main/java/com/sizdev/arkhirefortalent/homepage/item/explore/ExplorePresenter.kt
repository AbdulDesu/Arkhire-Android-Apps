package com.sizdev.arkhirefortalent.homepage.item.explore

import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ExplorePresenter (private val coroutineScope: CoroutineScope,
                        private val service: ArkhireApiService?) : ExploreContract.Presenter {

    private var view: ExploreContract.View? = null

    override fun bindToView(view: ExploreContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun exploreProject() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.startExplore()
                } catch (e: HttpException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        when {
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

            if (result is ExploreResponse) {
                if (result.success) {
                    val list = result.data?.map{
                        ExploreModel(it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.projectSalary, it.projectImage, it.companyID, it.companyName, it.companyImage, it.postedAt)
                    }
                    view?.hideProgressBar()
                    view?.showExploreList(list)
                }
                else {
                    view?.hideProgressBar()
                    view?.setError(result.message)
                }
            }
        }
    }

    override fun searchProject(search: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.searchExplore(search)
                } catch (e: HttpException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        when {
                            e.code() == 403 -> {
                                view?.setError("Session Expired !")
                            }

                            e.code() == 404 -> {
                                view?.setError("Nothing Found !")
                            }

                            else -> {
                                view?.setError("Unknown Error, Please Try Again Later !")
                            }
                        }
                    }
                }
            }

            if (result is ExploreResponse) {
                if (result.success) {
                    val list = result.data?.map {
                        ExploreModel(it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.projectSalary, it.projectImage, it.companyID, it.companyName, it.companyImage, it.postedAt)
                    }
                    view?.hideProgressBar()
                    view?.showExploreList(list)
                } else {
                    view?.hideProgressBar()
                    view?.setError(result.message)
                }
            }
        }
    }
}