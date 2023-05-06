package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        var log : Button = findViewById<Button>(R.id.transportSectionButton)

        log.setOnClickListener{
            val intent = Intent(this, TransportActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}