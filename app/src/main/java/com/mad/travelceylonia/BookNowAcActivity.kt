package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class BookNowAcActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    lateinit var dataList: ArrayList<String>;
    lateinit var hotels: ArrayList<Hotel>;
    private lateinit var arrayAdapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_now_ac_activity)

        dataList = ArrayList()
        hotels = ArrayList()

        database = FirebaseDatabase.getInstance().getReference("Hotels")
        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.itemlistAco)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, dataList)
        mListView.adapter = arrayAdapter

        mListView.setOnItemClickListener { parent, _, position, _ ->
            val hotel = hotels[position]

            val intent = Intent(this, BookHotelActivity::class.java)
            intent.putExtra("hotel", hotel)
            startActivity(intent)

        }


        getData();
    }

    private fun getData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("DATA FROM DB", "$snapshot")
                for (ds in snapshot.children){
                    val id = ds.key
                    val name = ds.child("name").value.toString()
                    val location = ds.child("location").value.toString()
                    val costPerDay = ds.child("costPerDay").value.toString()
                    val stars = ds.child("stars").value.toString()

                    val info = "$name - $location ($stars stars)"
                    dataList.add(info)
                    hotels.add(Hotel(id.toString(),name,location,costPerDay.toInt(),stars.toInt()))
                }
                arrayAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@BookNowAcActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}