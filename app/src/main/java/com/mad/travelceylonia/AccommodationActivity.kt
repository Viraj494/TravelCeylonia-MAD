package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AccommodationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accommodation)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        var log : Button = findViewById<Button>(R.id.bookNowButtonA)

        log.setOnClickListener{
            val intent = Intent(this, BookNowAcActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        var view : Button = findViewById<Button>(R.id.viewBookingButtonA)

        view.setOnClickListener{
            val intent = Intent(this, ManageBookingAcActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}