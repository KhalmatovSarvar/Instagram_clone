package com.example.instagram_clone.manager.handler

import com.example.instagram_clone.model.Post
import com.example.instagram_clone.model.User
import java.lang.Exception

interface DBPostsHandler {
    fun onSuccess(posts: ArrayList<Post>)
    fun onError(e:Exception)
}