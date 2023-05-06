package com.pasindusapplication.app.modules.confirmpaymentdetailsone.`data`.model

import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ConfirmPaymentDetailsOneModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg_enter_your_car)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtCardNumber: String? = MyApp.getInstance().resources.getString(R.string.lbl_card_number)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtExpiration: String? = MyApp.getInstance().resources.getString(R.string.lbl_expiration)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtCCV: String? = MyApp.getInstance().resources.getString(R.string.lbl_ccv)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleFiveValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleEightValue: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  var etRectangleNineValue: String? = null
)
