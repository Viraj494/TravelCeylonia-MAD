package com.pasindusapplication.app.modules.selectpaymentmethod.`data`.model

import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.di.MyApp
import kotlin.String

data class SelectPaymentMethodModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtSelectYourPay: String? =
      MyApp.getInstance().resources.getString(R.string.msg_select_your_pay)

)
