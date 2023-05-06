package com.pasindusapplication.app.modules.confirmpaymentdetails.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasindusapplication.app.modules.confirmpaymentdetails.`data`.model.ConfirmPaymentDetailsModel
import org.koin.core.KoinComponent

class ConfirmPaymentDetailsVM : ViewModel(), KoinComponent {
    val etGroupSixValue = MutableLiveData<String>()
    val etGroupFiveValue = MutableLiveData<String>()
    val etGroupFourValue = MutableLiveData<String>()
    val etGroupThreeValue = MutableLiveData<String>()
  val confirmPaymentDetailsModel: MutableLiveData<ConfirmPaymentDetailsModel> =
      MutableLiveData(ConfirmPaymentDetailsModel())

  var navArguments: Bundle? = null
}
