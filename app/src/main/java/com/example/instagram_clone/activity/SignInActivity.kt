package com.example.instagram_clone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagram_clone.R

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
        b_sign_in.setOnClickListener { callMainActivity() }

        val tv_sign_up = findViewById<TextView>(R.id.tv_sign_up)
        tv_sign_up.setOnClickListener { callSignUpActivity() }

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