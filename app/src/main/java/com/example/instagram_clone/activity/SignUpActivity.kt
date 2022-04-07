package com.example.instagram_clone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.instagram_clone.R


/*
in SignUpActivity user can sign up using fullname ,password ,email
 */
class SignUpActivity : BaseActivity() {

    val TAG = SignUpActivity::class.java

    lateinit var et_fullname:EditText
    lateinit var et_password:EditText
    lateinit var et_email:EditText
    lateinit var et_cpassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initViews()
    }

    private fun initViews() {

    }
}