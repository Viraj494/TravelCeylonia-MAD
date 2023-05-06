package com.pasindusapplication.app.modules.addpaymentdetails.`data`.model

import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.di.MyApp
import kotlin.String

data class AddPaymentDetailsModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtEnterYourPaym: String? =
      MyApp.getInstance().resources.getString(R.string.msg_enter_your_paym)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupTwentyValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupNineteenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupEighteenValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupSeventeenValue: String? = null
)
