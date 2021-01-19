package com.sizdev.arkhirefortalent.homepage.item.home

import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectModel
import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectResponse
import com.sizdev.arkhirefortalent.networking.ArkhireApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class HomePresenter (private val coroutineScope: CoroutineScope,
                     private val service: ArkhireApiService?) : HomeContract.Presenter {

    private var view: HomeContract.View? = null

    override fun bindToView(view: HomeContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun getAccountName(accountID: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getAccountResponse(accountID)
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

            if (result is HomeResponse) {
                if (result.success){
                    view?.setGreeting(result.data[0].accountName)
                }
                else {
                    view?.hideProgressBar()
                    view?.setError(result.message)
                }
            }
        }
    }

    override fun getHighlightedProject(accountID: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getNewerProjectResponse(accountID)
                } catch (e: HttpException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){

                        when{
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

            if (result is ProjectResponse) {
                if (result.success){
                    val list = result.data?.map{
                        ProjectModel(it.contributorID, it.talentID, it.companyID, it.companyName, it.companyImage, it.projectTittle, it.projectDesc, it.projectDuration, it.projectSalary, it.projectImage, it.offeringID, it.hiringStatus, it.offeredSalary, it.replyMsg)
                    }
                    view?.hideProgressBar()
                    view?.addHighlightProject(list)
                }
                else {
                    view?.hideProgressBar()
                    view?.setError(result.message)
                }
            }
        }
    }
}