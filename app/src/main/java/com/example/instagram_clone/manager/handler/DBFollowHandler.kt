package com.example.instagram_clone.manager.handler

import java.lang.Exception

interface DBFollowHandler {
    fun onSuccess(isDone: Boolean)
    fun onError(e:Exception)
}