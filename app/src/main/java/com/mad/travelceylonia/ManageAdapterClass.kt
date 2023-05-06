package com.mad.travelceylonia

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ManageAdapterClass (private val dataList: ArrayList<BookingDataManage>): RecyclerView.Adapter<ManageAdapterClass.ViewHolderClass> (){

    var onItemClick: ((BookingDataManage) -> Unit)? = null

    private lateinit var c : Context
    private lateinit var database: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_manage, parent, false)
        c = parent.context
        database = FirebaseDatabase.getInstance().getReference("Bookings")
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]

        holder.rVehicle.text = currentItem.vehicle
        holder.rDateRange.text = "Date range : "+currentItem.fromDate + " - " +currentItem.toDate
        holder.rCost.text = "Total Cost : "+currentItem.totalCost.toString()
        holder.rIsPaid.text = "Is Paid : "+currentItem.isPaid

        holder.editButton.setOnClickListener{
            var intent = Intent(this.c, EditBookingActivity::class.java)
            intent.putExtra("booking", currentItem)
            c.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            val builder = AlertDialog.Builder(c)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    database.child(currentItem.id).removeValue().addOnSuccessListener {
                        var intent = Intent(this.c, ManageBookingActivity::class.java)
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
        val rVehicle: TextView = itemView.findViewById(R.id.bookedVehicle)
        val rDateRange: TextView = itemView.findViewById(R.id.bookedDateRange)
        val rCost: TextView = itemView.findViewById(R.id.bookedCost)
        val rIsPaid: TextView = itemView.findViewById(R.id.bookedPaid)
        val editButton: TextView = itemView.findViewById(R.id.editBookingButton)
        val deleteButton: TextView = itemView.findViewById(R.id.deleteBookingButton)
    }
}