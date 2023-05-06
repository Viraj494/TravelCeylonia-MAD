package com.pasindusapplication.app.modules.confirmpaymentdetailsone.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.database.FirebaseDatabase
import com.pasindusapplication.app.R
import com.pasindusapplication.app.appcomponents.base.BaseActivity
import com.pasindusapplication.app.databinding.ActivityConfirmPaymentDetailsOneBinding
import com.pasindusapplication.app.modules.addpaymentdetails.ui.AddPaymentDetailsActivity
import com.pasindusapplication.app.modules.confirmpaymentdetailsone.`data`.viewmodel.ConfirmPaymentDetailsOneVM
import com.pasindusapplication.app.modules.selectpaymentmethodone.ui.SelectPaymentMethodOneActivity
import kotlin.String
import kotlin.Unit

class ConfirmPaymentDetailsOneActivity :
    BaseActivity<ActivityConfirmPaymentDetailsOneBinding>(R.layout.activity_confirm_payment_details_one)
    {
  private val viewModel: ConfirmPaymentDetailsOneVM by viewModels<ConfirmPaymentDetailsOneVM>()
      private var paymentId: String? = null
  override fun onInitialized(): Unit {
    paymentId = intent.getStringExtra("paymentId")
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.confirmPaymentDetailsOneVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnCancel.setOnClickListener {
      val destIntent = SelectPaymentMethodOneActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnConfirm.setOnClickListener {
      showConfirmationDialog()
    }
  }
      private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Do you want to keep these details?")

        builder.setPositiveButton("Yes") { _, _ ->
          // Proceed with your confirmation action, e.g., storing data in Firebase
          val destIntent = SelectPaymentMethodOneActivity.getIntent(this, null)
          startActivity(destIntent)
        }

        builder.setNegativeButton("No") { _, _ ->
          // Delete data from Firebase database
          deleteDataFromFirebase()

        }

        builder.setNeutralButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
      }

      private fun deleteDataFromFirebase() {
        // Assuming you have the database reference and the specific node key to delete
        val database = FirebaseDatabase.getInstance()
        val dataRef = database.getReference("payments")

        dataRef.child(paymentId.toString()).removeValue()
          .addOnSuccessListener {
            Toast.makeText(this, "Data deleted successfully.", Toast.LENGTH_SHORT).show()
            val destIntent = SelectPaymentMethodOneActivity.getIntent(this, null)
            startActivity(destIntent)
          }
          .addOnFailureListener { exception ->
            Toast.makeText(this, "Error deleting data: ${exception.message}", Toast.LENGTH_SHORT).show()
          }
      }

      companion object {
    const val TAG: String = "CONFIRM_PAYMENT_DETAILS_ONE_ACTIVITY"


    fun getIntent(context: Context, bundle: String?): Intent {
      val destIntent = Intent(context, ConfirmPaymentDetailsOneActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
