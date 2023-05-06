package com.mad.travelceylonia

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils


class TransportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        var log : Button = findViewById<Button>(R.id.bookNowButton)

        log.setOnClickListener{
            val intent = Intent(this, BookNowActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        var view : Button = findViewById<Button>(R.id.viewBookingButton)

        view.setOnClickListener{
            val intent = Intent(this, ManageBookingActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}