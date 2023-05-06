package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat

class BookingDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        val booking = intent.getParcelableExtra<BookingData>("booking")

        if(booking != null){
            val vehiName: TextView = findViewById(R.id.vehiNameTxt)
            val fromDate: TextView = findViewById(R.id.fromDateTxt)
            val toDate: TextView = findViewById(R.id.toDateTxt)
            val noOfDays: TextView = findViewById(R.id.noOfdaysTxt)
            val totalCost: TextView = findViewById(R.id.totalCostTxt)

            vehiName.text = "Vehicle : "+booking.vehicle
            fromDate.text = "From : "+booking.fromDate
            toDate.text = "To : "+booking.toDate
            noOfDays.text = "No of days : "+booking.noOfDays.toString()
            totalCost.text = "Total Cost : $"+booking.totalCost.toString()


        }

        var cancel : Button = findViewById<Button>(R.id.cancelButton)

        cancel.setOnClickListener{
            val intent = Intent(this, ManageBookingActivity::class.java)
            startActivity(intent)
        }


        var pay : Button = findViewById<Button>(R.id.payButton)

        pay.setOnClickListener{
            Toast.makeText(this, "Processing Payment", Toast.LENGTH_SHORT).show()
        }
    }
}