package com.sizdev.arkhirefortalent.homepage.item.home.project.allproject

import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ShowAllProjectPresenter(private val coroutineScope: CoroutineScope,
                              private val service: ArkhireApiService?) : ShowAllProjectContract.Presenter {

    private var view: ShowAllProjectContract.View? = null

    override fun bindToView(view: ShowAllProjectContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun getCurrentProject(accountID: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getAllProjectResponse(accountID)
                } catch (e: HttpException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        when {
                            e.code() == 403 -> {
                                view?.setError("Session Expired !")
                            }

                            e.code() == 404 -> {
                                view?.setError("Project Not Found !")
                            }

                            else -> {
                                view?.setError("Unknown Error, Please Try Again Later !")
                            }
                        }
                    }
                }
            }

            if (result is ShowAllProjectResponse) {
                if (result.success) {
                    val list = result.data?.map{
                        ShowAllProjectModel(it.offeringID, it.projectID, it.projectTitle, it.projectDuration, it.projectDesc, it.projectSallary, it.hiringStatus, it.offeredSalary, it.replyMsg, it.repliedAt)
                    }
                    view?.addAllProjectList(list)
                    view?.hideProgressBar()

                } else {
                    view?.hideProgressBar()
                    view?.setError(result.message)
                }
            }
        }
    }
}