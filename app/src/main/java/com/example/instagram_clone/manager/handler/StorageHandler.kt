package com.example.instagram_clone.manager.handler

interface StorageHandler {
    fun onSuccess(imgUrl:String)
    fun onError(e:Exception)
}