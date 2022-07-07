package com.example.instagram_clone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.instagram_clone.R
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.handler.AuthHandler
import com.example.instagramclone.utils.Extensions.toast

/*
In SignInActivity, user can login usimg email, password
 */
class SignInActivity : BaseActivity() {

    val TAG = SignInActivity::class.java.toString()

    lateinit var et_email: EditText
    lateinit var et_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initViews()
    }

    private fun initViews() {
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)

        val b_sign_in = findViewById<Button>(R.id.b_signin)
        b_sign_in.setOnClickListener {

            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()

            if(email.isNotEmpty() && password.isNotEmpty())
                fireBaseSignIn(email,password)


        }

        val tv_sign_up = findViewById<TextView>(R.id.tv_sign_up)
        tv_sign_up.setOnClickListener { callSignUpActivity() }

    }

    private fun fireBaseSignIn(email: String, password: String) {
        showLoading(this)
        AuthManager.signIn(email,password,object :AuthHandler{
            override fun onSuccess(uid: String) {
                dissmissLoading()
                toast(getString(R.string.str_sign_in_success))
                callMainActivity()
            }

            override fun onError(exception: Exception?) {
                toast(getString(R.string.str_sign_in_failed))
                Log.d("XATO",exception.toString())

            }
        })

    }

    private fun callSignUpActivity() {
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}