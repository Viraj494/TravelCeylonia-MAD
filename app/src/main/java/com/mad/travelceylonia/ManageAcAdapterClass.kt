package com.mad.travelceylonia

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ManageAcAdapterClass (private val dataList: ArrayList<ManageHotelBookingData>): RecyclerView.Adapter<ManageAcAdapterClass.ViewHolderClass> (){

    var onItemClick: ((ManageHotelBookingData) -> Unit)? = null

    private lateinit var c : Context
    private lateinit var database: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_manage_ac, parent, false)
        c = parent.context
        database = FirebaseDatabase.getInstance().getReference("HotelBookings")
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]

        holder.rHotel.text = currentItem.hotel
        holder.rDateRange.text = "Date range : "+currentItem.fromDate + " - " +currentItem.toDate
        holder.rRooms.text = "No of Rooms : " + currentItem.noOfRooms
        holder.rCost.text = "Total Cost : "+currentItem.totalCost.toString()
        holder.rIsPaid.text = "Is Paid : "+currentItem.isPaid

        holder.editButton.setOnClickListener{
            var intent = Intent(this.c, EditAcBookingActivity::class.java)
            intent.putExtra("booking", currentItem)
            c.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            val builder = AlertDialog.Builder(c)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    database.child(currentItem.id).removeValue().addOnSuccessListener {
                        var intent = Intent(this.c, ManageBookingAcActivity::class.java)
                        c.startActivity(intent)
                    }.addOnFailureListener{
                        Toast.makeText(c,"Deleting Failed",Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }



    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rHotel: TextView = itemView.findViewById(R.id.bookedHotel)
        val rDateRange: TextView = itemView.findViewById(R.id.bookedDateRangeA)
        val rCost: TextView = itemView.findViewById(R.id.bookedCostA)
        val rRooms: TextView = itemView.findViewById(R.id.bookedRooms)
        val rIsPaid: TextView = itemView.findViewById(R.id.bookedPaidA)
        val editButton: TextView = itemView.findViewById(R.id.editBookingButtonA)
        val deleteButton: TextView = itemView.findViewById(R.id.deleteBookingButtonA)
    }
}