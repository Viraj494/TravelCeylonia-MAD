package com.mad.travelceylonia

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass (private val dataList: ArrayList<DataClass>): RecyclerView.Adapter<AdapterClass.ViewHolderClass> (){

    var onItemClick: ((DataClass) -> Unit)? = null

    private lateinit var c : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        c = parent.context
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.rvImage.setImageResource(currentItem.dataImage)
        holder.rvTitle.text = currentItem.dataTitle
        holder.rvLoc.text = currentItem.dataLoc

        holder.itemView.setOnClickListener{

            var intent = Intent(this.c, VehicleDetailActivity::class.java)
            intent.putExtra("vehicle", currentItem)
            c.startActivity(intent)

        }
    }



    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.v_image)
        val rvTitle: TextView = itemView.findViewById(R.id.v_title)
        val rvLoc: TextView = itemView.findViewById(R.id.v_location)
    }
}