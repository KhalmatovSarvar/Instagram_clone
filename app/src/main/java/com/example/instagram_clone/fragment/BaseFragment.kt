package com.example.instagram_clone.fragment

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.instagram_clone.activity.SignInActivity

open class BaseFragment:Fragment() {
        fun callSignInActivity(activity: Activity){
            val intent = Intent(activity,SignInActivity::class.java)
            startActivity(intent)
            activity.finish()
        }
}