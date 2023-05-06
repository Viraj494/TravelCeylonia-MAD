package com.pasindusapplication.app.modules.selectpaymentmethod.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pasindusapplication.app.R
import com.pasindusapplication.app.databinding.RowListcontrastBinding
import com.pasindusapplication.app.modules.selectpaymentmethod.`data`.model.ListcontrastRowModel
import kotlin.Int
import kotlin.collections.List

class ListcontrastAdapter(
  var list: List<ListcontrastRowModel>
) : RecyclerView.Adapter<ListcontrastAdapter.RowListcontrastVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListcontrastVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_listcontrast,parent,false)
    return RowListcontrastVH(view)
  }

  override fun onBindViewHolder(holder: RowListcontrastVH, position: Int) {
    val listcontrastRowModel = ListcontrastRowModel()
    // TODO uncomment following line after integration with data source
    // val listcontrastRowModel = list[position]
    holder.binding.listcontrastRowModel = listcontrastRowModel
  }

  override fun getItemCount(): Int = 2
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<ListcontrastRowModel>) {
    list = newData
    notifyDataSetChanged()
  }

  fun setOnItemClickListener(clickListener: OnItemClickListener) {
    this.clickListener = clickListener
  }

  interface OnItemClickListener {
    fun onItemClick(
      view: View,
      position: Int,
      item: ListcontrastRowModel
    ) {
    }
  }

  inner class RowListcontrastVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowListcontrastBinding = RowListcontrastBinding.bind(itemView)
  }
}
