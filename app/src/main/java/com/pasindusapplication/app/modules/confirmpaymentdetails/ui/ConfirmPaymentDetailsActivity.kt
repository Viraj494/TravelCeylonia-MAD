package com.pasindusapplication.app.modules.confirmpaymentdetails.ui

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.base.BaseActivity
import com.pasindusapplication.app.databinding.ActivityConfirmPaymentDetailsBinding
import com.pasindusapplication.app.modules.confirmpaymentdetails.`data`.viewmodel.ConfirmPaymentDetailsVM
import com.pasindusapplication.app.modules.confirmpaymentdetailsone.ui.ConfirmPaymentDetailsOneActivity
import kotlin.String
import kotlin.Unit

class ConfirmPaymentDetailsActivity :
    BaseActivity<ActivityConfirmPaymentDetailsBinding>(R.layout.activity_confirm_payment_details) {
//  private val viewModel: ConfirmPaymentDetailsVM by viewModels<ConfirmPaymentDetailsVM>()
  private val viewModel: ConfirmPaymentDetailsVM by viewModels()
  private var paymentId: String? = null

  override fun onInitialized(): Unit {
    paymentId = intent.getStringExtra("paymentId")
    if (paymentId != null) {
      val database = FirebaseDatabase.getInstance()
      val reference = database.getReference("payments").child(paymentId!!)
      reference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
          viewModel.etGroupSixValue.value = dataSnapshot.child("name").value.toString()
          viewModel.etGroupFiveValue.value = dataSnapshot.child("email").value.toString()
          viewModel.etGroupFourValue.value = dataSnapshot.child("phone").value.toString()
          viewModel.etGroupThreeValue.value = dataSnapshot.child("amount").value.toString()
          binding.confirmPaymentDetailsVM = viewModel
        }

        override fun onCancelled(databaseError: DatabaseError) {
          // Handle error
        }
      })
    }
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.confirmPaymentDetailsVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnPayment.setOnClickListener {
      val destIntent = ConfirmPaymentDetailsOneActivity.getIntent(this, paymentId)
      startActivity(destIntent)
    }
    binding.btnEdit.setOnClickListener {
      if (paymentId != null) {
        val updatedData = hashMapOf(
          "name" to viewModel.etGroupSixValue.value,
          "email" to viewModel.etGroupFiveValue.value,
          "phone" to viewModel.etGroupFourValue.value,
          "amount" to viewModel.etGroupThreeValue.value
        )
        val reference = FirebaseDatabase.getInstance().getReference("payments").child(paymentId!!)
        reference.updateChildren(updatedData as Map<String, Any>).addOnSuccessListener {
          Toast.makeText(this, "Payment details updated successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { e ->
          Toast.makeText(this, "Failed to update payment details: ${e.message}", Toast.LENGTH_SHORT).show()
        }
      }
    }

  }

  companion object {
    const val TAG: String = "CONFIRM_PAYMENT_DETAILS_ACTIVITY"


    fun getIntent(context: Context, paymentId: String?): Intent {
      val destIntent = Intent(context, ConfirmPaymentDetailsActivity::class.java)
      destIntent.putExtra("paymentId", paymentId)
      return destIntent
    }

  }
}
