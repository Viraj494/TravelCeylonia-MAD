package com.pasindusapplication.app.modules.selectpaymentmethod.`data`.model

import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ListcontrastRowModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtCash: String? = MyApp.getInstance().resources.getString(R.string.lbl_cash)

)
