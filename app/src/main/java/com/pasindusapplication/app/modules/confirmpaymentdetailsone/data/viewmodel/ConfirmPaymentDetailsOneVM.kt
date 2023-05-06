package com.pasindusapplication.app.modules.confirmpaymentdetailsone.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasindusapplication.app.modules.confirmpaymentdetailsone.`data`.model.ConfirmPaymentDetailsOneModel
import org.koin.core.KoinComponent

class ConfirmPaymentDetailsOneVM : ViewModel(), KoinComponent {
  val confirmPaymentDetailsOneModel: MutableLiveData<ConfirmPaymentDetailsOneModel> =
      MutableLiveData(ConfirmPaymentDetailsOneModel())

  var navArguments: Bundle? = null
}
