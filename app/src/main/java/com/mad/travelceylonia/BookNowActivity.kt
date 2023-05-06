package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class BookNowActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var imageList: Array<Int>
    lateinit var titleList: ArrayList<String>
    lateinit var descList: ArrayList<String>
    lateinit var detailImageList: ArrayList<Int>
    private lateinit var myAdapter: AdapterClass
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<DataClass>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_now)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance().getReference("Vehicles")

        titleList = ArrayList()
        descList = ArrayList()
        detailImageList = ArrayList()

         imageList = arrayOf(
                    R.drawable.car,
                    R.drawable.minivan,
                    R.drawable.van,
                    R.drawable.kdh,
                    R.drawable.bus,
                    R.drawable.tuk)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList = arrayListOf<DataClass>()
        searchList = arrayListOf<DataClass>()
        getData()

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchtText = newText!!.toLowerCase(Locale.getDefault())
                if (searchtText.isNotEmpty()){
                    dataList.forEach{
                        if (it.dataTitle.toLowerCase(Locale.getDefault()).contains(searchtText)){
                            searchList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                }else{
                    searchList.clear()
                    searchList.addAll(dataList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })

        myAdapter = AdapterClass(searchList)
        myAdapter.onItemClick = {
            Log.e("Adapter","Clicked")
        }
        recyclerView.adapter = myAdapter
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

    private fun getData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("DATA FROM DB", "$snapshot")
                for (ds in snapshot.children){
                    val id = ds.key.toString()
                    val name = ds.child("name").value.toString()
                    val type = ds.child("type").value.toString()
                    val desc = ds.child("description").value.toString()
                    val loc = ds.child("location").value.toString()
                    val fuel = ds.child("fuel").value.toString()
                    val seats = ds.child("seats").value.toString()
                    val cost = ds.child("perDayCost").value.toString().toInt()

                    var vehicleImage = 0;
                    when (type) {
                        "Car" -> {
                            vehicleImage = imageList[0];
                        }
                        "MiniVan" -> {
                            vehicleImage = imageList[1];
                        }
                        "Van" -> {
                            vehicleImage = imageList[2];
                        }
                        "KDH" -> {
                            vehicleImage = imageList[3];
                        }
                        "Bus" -> {
                            vehicleImage = imageList[4];
                        }
                        "Tuk" -> {
                            vehicleImage = imageList[5];
                        }
                    }

                    val dataClass = DataClass(id,vehicleImage, name, desc, vehicleImage,loc,fuel,seats,cost)
                    dataList.add(dataClass)
                }

                searchList.addAll(dataList)
                recyclerView.adapter = AdapterClass(searchList)
                //myAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@BookNowActivity, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }



}