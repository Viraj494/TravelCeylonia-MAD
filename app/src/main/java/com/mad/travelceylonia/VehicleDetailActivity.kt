package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class VehicleDetailActivity : AppCompatActivity() {

    lateinit var vehicle : DataClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_detail)

        val getData = intent.getParcelableExtra<DataClass>("vehicle")
        if(getData != null){

            vehicle = getData

            val detailTitle: TextView = findViewById(R.id.detailTitle)
            val detailDesc: TextView = findViewById(R.id.detailDesc)
            val detailImage: ImageView = findViewById(R.id.detailImage)
            val seats: TextView = findViewById(R.id.seatsTxt)
            val fuel: TextView = findViewById(R.id.fuelTxt)
            val cost: TextView = findViewById(R.id.costTxt)

            detailTitle.text = getData.dataTitle
            detailDesc.text = getData.dataDesc
            seats.text = "No of Seats : "+getData.dataSeats
            fuel.text = "Fuel type : "+getData.dataFuel
            cost.text = "Cost per day : $"+getData.dataCost.toString()
            detailImage.setImageResource((getData.dataDetailImage))

        }

        var log : Button = findViewById<Button>(R.id.bookVehicleButton)

        log.setOnClickListener{
            val intent = Intent(this, BookVehicleActivity::class.java)
            intent.putExtra("vehicle", vehicle)
            startActivity(intent)
        }

    }
}