package com.pasindusapplication.app.modules.selectpaymentmethodone.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pasindusapplication.app.R
import com.pasindusapplication.app.databinding.RowListrefreshBinding
import com.pasindusapplication.app.modules.selectpaymentmethodone.`data`.model.ListrefreshRowModel
import kotlin.Int
import kotlin.collections.List

class ListrefreshAdapter(
  var list: List<ListrefreshRowModel>
) : RecyclerView.Adapter<ListrefreshAdapter.RowListrefreshVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListrefreshVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_listrefresh,parent,false)
    return RowListrefreshVH(view)
  }

  override fun onBindViewHolder(holder: RowListrefreshVH, position: Int) {
    val listrefreshRowModel = ListrefreshRowModel()
    // TODO uncomment following line after integration with data source
    // val listrefreshRowModel = list[position]
    holder.binding.listrefreshRowModel = listrefreshRowModel
  }

  override fun getItemCount(): Int = 2
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<ListrefreshRowModel>) {
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
      item: ListrefreshRowModel
    ) {
    }
  }

  inner class RowListrefreshVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowListrefreshBinding = RowListrefreshBinding.bind(itemView)
  }
}
