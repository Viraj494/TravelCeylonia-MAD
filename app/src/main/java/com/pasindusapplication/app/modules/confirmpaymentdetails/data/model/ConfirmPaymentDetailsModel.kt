package com.pasindusapplication.app.modules.confirmpaymentdetails.`data`.model

import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ConfirmPaymentDetailsModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtConfirmYourPa: String? =
      MyApp.getInstance().resources.getString(R.string.msg_confirm_your_pa)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupSixValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupFiveValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupFourValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etGroupThreeValue: String? = null
)
