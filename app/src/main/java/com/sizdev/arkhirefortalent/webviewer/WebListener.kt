package com.sizdev.arkhirefortalent.webviewer

interface WebListener {
    fun onPageStarted()
    fun onPageFinish()
    fun onShouldOverrideUrl(redirectUrl:String)
    fun onProgressChage(progress: Int)
    fun onReceivedError()
}