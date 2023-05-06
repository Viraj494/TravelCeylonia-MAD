package com.pasindusapplication.app.modules.selectpaymentmethodone.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.base.BaseActivity
import com.pasindusapplication.app.databinding.ActivitySelectPaymentMethodOneBinding
import com.pasindusapplication.app.modules.selectpaymentmethodone.`data`.model.ListrefreshRowModel
import com.pasindusapplication.app.modules.selectpaymentmethodone.`data`.viewmodel.SelectPaymentMethodOneVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class SelectPaymentMethodOneActivity :
    BaseActivity<ActivitySelectPaymentMethodOneBinding>(R.layout.activity_select_payment_method_one)
    {
  private val viewModel: SelectPaymentMethodOneVM by viewModels<SelectPaymentMethodOneVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val listrefreshAdapter =
    ListrefreshAdapter(viewModel.listrefreshList.value?:mutableListOf())
    binding.recyclerListrefresh.adapter = listrefreshAdapter
    listrefreshAdapter.setOnItemClickListener(
    object : ListrefreshAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : ListrefreshRowModel) {
        onClickRecyclerListrefresh(view, position, item)
      }
    }
    )
    viewModel.listrefreshList.observe(this) {
      listrefreshAdapter.updateData(it)
    }
    binding.selectPaymentMethodOneVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  fun onClickRecyclerListrefresh(
    view: View,
    position: Int,
    item: ListrefreshRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "SELECT_PAYMENT_METHOD_ONE_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, SelectPaymentMethodOneActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
