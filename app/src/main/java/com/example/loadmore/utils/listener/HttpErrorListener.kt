package com.example.loadmore.utils.listener

interface HttpErrorListener {
    fun networkFailure()
    fun timeOutError()
    fun errorMessage()
    fun errorMessage(error:String)
    fun loadingProgressbar()
    fun hideLoading()
    fun serverNotResponse(error: Int)
}