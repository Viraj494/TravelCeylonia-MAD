package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ManageBookingActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<BookingDataManage>

    private lateinit var myAdapter: ManageAdapterClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_booking)

        database = FirebaseDatabase.getInstance().getReference("Bookings")

        var back : Button = findViewById<Button>(R.id.backToTransportButton)

        back.setOnClickListener{
            val intent = Intent(this, TransportActivity::class.java)
            startActivity(intent)
        }

        dataList = arrayListOf<BookingDataManage>()
        getData()



        recyclerView = findViewById(R.id.recyclerViewManage)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        myAdapter = ManageAdapterClass(dataList)
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
                            val vehicle = ds.child("vehicle").value.toString()
                            val isPaid = ds.child("paid").value.toString()
                            val noOfDays = ds.child("noOfDays").value.toString()
                            var perDayCost = tCost.toLong()/noOfDays.toLong()

                            val bookingDataManage = BookingDataManage(id,vehicle, fDate, tDate, noOfDays.toLong(),tCost.toLong(),owner,perDayCost.toInt(),isPaid)
                            dataList.add(bookingDataManage)
                        }
                    }
                }
                recyclerView.adapter = ManageAdapterClass(dataList)
                //myAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ManageBookingActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}