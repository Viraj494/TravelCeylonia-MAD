package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class
TourGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_guide)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        var log : Button = findViewById<Button>(R.id.bookNowButtonTG)

        log.setOnClickListener{
            val intent = Intent(this, BookNowTGActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        var view : Button = findViewById<Button>(R.id.viewBookingButtonTG)

        view.setOnClickListener{
            val intent = Intent(this, ManageBookingTGActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}