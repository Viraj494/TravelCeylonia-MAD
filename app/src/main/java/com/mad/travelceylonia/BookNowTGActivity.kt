package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class BookNowTGActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    lateinit var dataList: ArrayList<String>;
    lateinit var guides: ArrayList<Guide>;
    private lateinit var arrayAdapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_now_tgactivity)

        dataList = ArrayList()
        guides = ArrayList()

        database = FirebaseDatabase.getInstance().getReference("Guides")
        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.itemlistTG)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, dataList)
        mListView.adapter = arrayAdapter

        mListView.setOnItemClickListener { parent, _, position, _ ->
            val guide = guides[position]

            val intent = Intent(this, BookGuideActivity::class.java)
            intent.putExtra("guide", guide)
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
                    val desc = ds.child("desc").value.toString()
                    val costPerDay = ds.child("costPerDay").value.toString()

                    val info = "$name ($desc)"
                    dataList.add(info)
                    guides.add(Guide(id.toString(),name,desc,costPerDay.toInt()))
                }
                arrayAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@BookNowTGActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}