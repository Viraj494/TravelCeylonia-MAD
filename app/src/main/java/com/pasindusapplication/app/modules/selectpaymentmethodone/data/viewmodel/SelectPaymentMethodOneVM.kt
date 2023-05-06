package com.pasindusapplication.app.modules.selectpaymentmethodone.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasindusapplication.app.modules.selectpaymentmethodone.`data`.model.ListrefreshRowModel
import com.pasindusapplication.app.modules.selectpaymentmethodone.`data`.model.SelectPaymentMethodOneModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class SelectPaymentMethodOneVM : ViewModel(), KoinComponent {
  val selectPaymentMethodOneModel: MutableLiveData<SelectPaymentMethodOneModel> =
      MutableLiveData(SelectPaymentMethodOneModel())

  var navArguments: Bundle? = null

  val listrefreshList: MutableLiveData<MutableList<ListrefreshRowModel>> =
      MutableLiveData(mutableListOf())
}
