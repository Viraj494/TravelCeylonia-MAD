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

class ManageBookingTGActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<ManageGuideBookingData>

    private lateinit var myAdapter: ManageTGAdapterClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_booking_tgactivity)

        database = FirebaseDatabase.getInstance().getReference("GuideBookings")

        var back : Button = findViewById<Button>(R.id.backToTourGuideButton)

        back.setOnClickListener{
            val intent = Intent(this, TourGuideActivity::class.java)
            startActivity(intent)
        }

        dataList = arrayListOf<ManageGuideBookingData>()
        getData()

        recyclerView = findViewById(R.id.recyclerViewManageTG)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        myAdapter = ManageTGAdapterClass(dataList)
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
                            val guide = ds.child("guide").value.toString()
                            val isPaid = ds.child("paid").value.toString()
                            val noOfDays = ds.child("noOfDays").value.toString()
                            var perDayCost = ds.child("costPerDay").value.toString().toInt()

                            val bookingDataManage = ManageGuideBookingData(id,guide,fDate,tDate, perDayCost,noOfDays.toInt(),tCost.toInt(),isPaid,owner)
                            dataList.add(bookingDataManage)
                        }
                    }
                }
                recyclerView.adapter = ManageTGAdapterClass(dataList)
                //myAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ManageBookingTGActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}