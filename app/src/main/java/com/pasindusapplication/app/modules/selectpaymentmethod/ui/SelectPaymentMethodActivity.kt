package com.pasindusapplication.app.modules.selectpaymentmethod.ui

import android.view.View
import androidx.activity.viewModels
import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.base.BaseActivity
import com.pasindusapplication.app.databinding.ActivitySelectPaymentMethodBinding
import com.pasindusapplication.app.modules.selectpaymentmethod.`data`.model.ListcontrastRowModel
import com.pasindusapplication.app.modules.selectpaymentmethod.`data`.viewmodel.SelectPaymentMethodVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class SelectPaymentMethodActivity :
    BaseActivity<ActivitySelectPaymentMethodBinding>(R.layout.activity_select_payment_method) {
  private val viewModel: SelectPaymentMethodVM by viewModels<SelectPaymentMethodVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val listcontrastAdapter =
    ListcontrastAdapter(viewModel.listcontrastList.value?:mutableListOf())
    binding.recyclerListcontrast.adapter = listcontrastAdapter
    listcontrastAdapter.setOnItemClickListener(
    object : ListcontrastAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : ListcontrastRowModel) {
        onClickRecyclerListcontrast(view, position, item)
      }
    }
    )
    viewModel.listcontrastList.observe(this) {
      listcontrastAdapter.updateData(it)
    }
    binding.selectPaymentMethodVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  fun onClickRecyclerListcontrast(
    view: View,
    position: Int,
    item: ListcontrastRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "SELECT_PAYMENT_METHOD_ACTIVITY"

  }
}
