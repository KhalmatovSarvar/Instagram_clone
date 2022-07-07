package com.example.instagram_clone.manager.handler

import com.example.instagram_clone.model.Post
import java.lang.Exception

interface DBPostHandler {
    fun onSuccess(post: Post? = null)
    fun onError(e:Exception)
}