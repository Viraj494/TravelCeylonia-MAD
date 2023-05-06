package com.pasindusapplication.app.modules.addpaymentdetails.ui

import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.database.FirebaseDatabase
import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.base.BaseActivity
import com.pasindusapplication.app.databinding.ActivityAddPaymentDetailsBinding
import com.pasindusapplication.app.modules.addpaymentdetails.`data`.viewmodel.AddPaymentDetailsVM
import com.pasindusapplication.app.modules.confirmpaymentdetails.ui.ConfirmPaymentDetailsActivity
import com.pasindusapplication.app.modules.selectpaymentmethodone.ui.SelectPaymentMethodOneActivity
import kotlin.String
import kotlin.Unit

class AddPaymentDetailsActivity :

    BaseActivity<ActivityAddPaymentDetailsBinding>(R.layout.activity_add_payment_details) {
  private val viewModel: AddPaymentDetailsVM by viewModels<AddPaymentDetailsVM>()

  override fun onInitialized(): Unit {

    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.addPaymentDetailsVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnBack.setOnClickListener {
      val destIntent = SelectPaymentMethodOneActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnProceed.setOnClickListener {
      saveDataToFirebase()

      val destIntent = ConfirmPaymentDetailsActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }
  private fun saveDataToFirebase() {
    val name = binding.etGroupTwenty.text.toString().trim()
    val email = binding.etGroupNineteen.text.toString().trim()
    val phone = binding.etGroupEighteen.text.toString().trim()
    val amount = binding.etGroupSeventeen.text.toString().trim()

    if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && amount.isNotEmpty()) {
      val database = FirebaseDatabase.getInstance()
      val reference = database.getReference("payments")

      val paymentId = reference.push().key
      val paymentDetails = hashMapOf(
        "name" to name,
        "email" to email,
        "phone" to phone,
        "amount" to amount
      )

      paymentId?.let {
        reference.child(it).setValue(paymentDetails).addOnSuccessListener {
          Toast.makeText(this, "Payment details saved successfully", Toast.LENGTH_SHORT).show()
          val destIntent = ConfirmPaymentDetailsActivity.getIntent(this, paymentId)
          startActivity(destIntent)

//          Toast.makeText(this, "Payment details saved successfully", Toast.LENGTH_SHORT).show()
//          val destIntent = ConfirmPaymentDetailsActivity.getIntent(this, null)
//          startActivity(destIntent)

        }.addOnFailureListener { e ->
          Toast.makeText(this, "Failed to save payment details: ${e.message}", Toast.LENGTH_SHORT).show()
        }
      }
    } else {
      Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
    }
  }
  companion object {
    const val TAG: String = "ADD_PAYMENT_DETAILS_ACTIVITY"

  }
}
