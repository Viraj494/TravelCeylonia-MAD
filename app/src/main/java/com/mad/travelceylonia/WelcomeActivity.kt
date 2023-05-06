package com.mad.travelceylonia

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        var log : Button = findViewById<Button>(R.id.visitNowButton)

        log.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}