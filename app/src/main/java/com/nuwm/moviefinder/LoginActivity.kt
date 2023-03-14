package com.nuwm.moviefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<AppCompatButton>(R.id.btnLogin).setOnClickListener {
            MainActivity.newIntent(this)
            finish()
        }
    }
}