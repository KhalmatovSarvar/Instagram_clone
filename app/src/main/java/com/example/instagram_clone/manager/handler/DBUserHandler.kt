package com.example.instagram_clone.manager.handler

import com.example.instagram_clone.model.User
import java.lang.Exception

interface DBUserHandler {
    fun onSuccess(user: User? = null)
    fun onError(e:Exception)
}