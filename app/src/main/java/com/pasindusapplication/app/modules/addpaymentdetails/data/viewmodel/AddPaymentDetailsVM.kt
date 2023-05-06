package com.pasindusapplication.app.modules.addpaymentdetails.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasindusapplication.app.modules.addpaymentdetails.`data`.model.AddPaymentDetailsModel
import org.koin.core.KoinComponent

class AddPaymentDetailsVM : ViewModel(), KoinComponent {
  val addPaymentDetailsModel: MutableLiveData<AddPaymentDetailsModel> =
      MutableLiveData(AddPaymentDetailsModel())

  var navArguments: Bundle? = null
}
