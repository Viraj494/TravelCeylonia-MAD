package com.pasindusapplication.app.modules.selectpaymentmethod.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasindusapplication.app.modules.selectpaymentmethod.`data`.model.ListcontrastRowModel
import com.pasindusapplication.app.modules.selectpaymentmethod.`data`.model.SelectPaymentMethodModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class SelectPaymentMethodVM : ViewModel(), KoinComponent {
  val selectPaymentMethodModel: MutableLiveData<SelectPaymentMethodModel> =
      MutableLiveData(SelectPaymentMethodModel())

  var navArguments: Bundle? = null

  val listcontrastList: MutableLiveData<MutableList<ListcontrastRowModel>> =
      MutableLiveData(mutableListOf())
}
