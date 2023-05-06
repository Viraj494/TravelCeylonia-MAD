package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ManageBookingAcActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<ManageHotelBookingData>

    private lateinit var myAdapter: ManageAcAdapterClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_booking_ac_activity)

        database = FirebaseDatabase.getInstance().getReference("HotelBookings")

        var back : Button = findViewById<Button>(R.id.backToAccomodationButton)

        back.setOnClickListener{
            val intent = Intent(this, AccommodationActivity::class.java)
            startActivity(intent)
        }

        dataList = arrayListOf()
        getData()

        recyclerView = findViewById(R.id.recyclerViewManageA)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        myAdapter = ManageAcAdapterClass(dataList)
        recyclerView.adapter = myAdapter

    }

    private fun getData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("DATA FROM DB", "$snapshot")
                for (ds in snapshot.children){
                    val id = ds.key.toString()
                    val owner = ds.child("bookedBy").value.toString()
                    val user = Firebase.auth.currentUser
                    if (user != null) {
                        if (user.email==owner){
                            val fDate = ds.child("fromDate").value.toString()
                            val tDate = ds.child("toDate").value.toString()
                            val tCost = ds.child("totalCost").value.toString()
                            val hotel = ds.child("hotel").value.toString()
                            val isPaid = ds.child("paid").value.toString()
                            val noOfDays = ds.child("noOfDays").value.toString()
                            val noOfRooms = ds.child("noOfRooms").value.toString()
                            var perDayCost = ds.child("costPerDay").value.toString().toInt()

                            val bookingDataManage = ManageHotelBookingData(id,hotel,fDate,tDate, perDayCost,noOfDays.toInt(),noOfRooms.toInt(),tCost.toInt(),isPaid,owner)
                            dataList.add(bookingDataManage)
                        }
                    }
                }
                recyclerView.adapter = ManageAcAdapterClass(dataList)
                //myAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ManageBookingAcActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}