package com.example.instagram_clone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagram_clone.R
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.DatabaseManager
import com.example.instagram_clone.manager.handler.AuthHandler
import com.example.instagram_clone.manager.handler.DBUserHandler
import com.example.instagram_clone.model.User
import com.example.instagramclone.utils.Extensions.toast


/*
in SignUpActivity user can sign up using fullname ,password ,email
 */
class SignUpActivity : BaseActivity() {

    val TAG = SignUpActivity::class.java

    lateinit var et_fullname: EditText
    lateinit var et_password: EditText
    lateinit var et_email: EditText
    lateinit var et_cpassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initViews()
    }

    private fun initViews() {
        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_cpassword = findViewById(R.id.et_confirm_password)
        val b_signup = findViewById<Button>(R.id.b_signup)
        b_signup.setOnClickListener {
            val fullname = et_fullname.text.toString().trim()
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (fullname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(fullname, email, password, "")
                fireBaseSignUp(user)
            }
        }
        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener {
            finish()
        }
    }

    private fun fireBaseSignUp(user: User) {
        AuthManager.signUp(user.email, user.password, object : AuthHandler {
            override fun onSuccess(uid: String) {
                user.uid = uid
                storeUserToDB(user)
                toast(getString(R.string.str_sign_up_success))

            }

            override fun onError(exception: Exception?) {
                toast(getString(R.string.str_sign_up_failed))

            }
        })
    }

    private fun storeUserToDB(user: User?) {
        DatabaseManager.storeUser(user, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                callMainActivity(context)
            }

            override fun onError(e: java.lang.Exception) {
            }
        })
    }
}