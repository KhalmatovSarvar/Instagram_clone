package com.example.instagram_clone.manager

import com.example.instagram_clone.manager.handler.DBUserHandler
import com.example.instagram_clone.model.User
import com.google.firebase.firestore.FirebaseFirestore

private var USER_PATH = "users"
private var POST_PATH = "posts"
private var FEED_PATH = "feeds"
private var FOLLOWING_PATH = "following"
private var FOLLOWERS_PATH = "followers"

object DatabaseManager {

    private var database = FirebaseFirestore.getInstance()

    fun store(user: User, handler: DBUserHandler){
        database.collection(USER_PATH).document(user.uid).set(user).addOnCompleteListener {
            handler.onSuccess()
        }
    }
}