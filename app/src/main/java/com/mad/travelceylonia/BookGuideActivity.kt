package com.mad.travelceylonia

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class BookGuideActivity : AppCompatActivity() {

    private lateinit var guideSelected : Guide
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_guide)

        database = FirebaseDatabase.getInstance().getReference("GuideBookings")

        val guideName: TextView = findViewById(R.id.guideName)
        val editTextDateFromTG: TextView = findViewById(R.id.editTextDateFromTG)
        val editTextDateToTG: TextView = findViewById(R.id.editTextDateToTG)
        val costTxtTG: TextView = findViewById(R.id.costTxtTG)

        var calculateCostButtonTG : Button = findViewById<Button>(R.id.calculateCostButtonTG)
        var confirmButtonTG : Button = findViewById<Button>(R.id.confirmButtonTG)


        var costPerDay = 0
        val guide = intent.getParcelableExtra<Guide>("guide")
        if(guide != null){
            guideSelected = guide
            guideName.text = guide.name
            costPerDay = guide.costPerDay

        }



        editTextDateFromTG.setOnClickListener {
            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    editTextDateFromTG.setText((dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year))
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.datePicker.descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
            datePickerDialog.show()
        }

        editTextDateToTG.setOnClickListener {
            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    editTextDateToTG.setText((dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year))
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.datePicker.descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
            datePickerDialog.show()
        }

        calculateCostButtonTG.setOnClickListener{

            if(editTextDateFromTG.text.toString().isEmpty() || editTextDateToTG.text.toString().isEmpty()){
                Toast.makeText(this,"Please Select Date Range", Toast.LENGTH_SHORT).show()
            }else{

                val dateDifference =
                    getDateDiff(SimpleDateFormat("dd/MM/yyyy"), editTextDateFromTG.text.toString(), editTextDateToTG.text.toString()) + 1

                val totalCost = costPerDay * dateDifference
                costTxtTG.text = "Total Cost : $$totalCost";

            }

        }



        confirmButtonTG.setOnClickListener{
            if(editTextDateFromTG.text.toString().isEmpty() || editTextDateToTG.text.toString().isEmpty()){
                Toast.makeText(this,"Please Select Date Range", Toast.LENGTH_SHORT).show()
            }else{

                val dateDifference =
                    getDateDiff(SimpleDateFormat("dd/MM/yyyy"), editTextDateFromTG.text.toString(), editTextDateToTG.text.toString()) + 1

                val totalCost = costPerDay * dateDifference

                val user = Firebase.auth.currentUser
                if (user != null) {
                    var booking = GuideBookingData(guideSelected.name,editTextDateFromTG.text.toString(), editTextDateToTG.text.toString(), guideSelected.costPerDay, dateDifference.toInt(), totalCost.toInt(), "no",user.email.toString())

                    //save record
                    database.push().setValue(booking).addOnSuccessListener {
                        //when successfully saved

                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Booking Completed. Please make the payment Now")
                            .setCancelable(false)
                            .setPositiveButton("Pay Now") { dialog, id ->
                                    Toast.makeText(this,"Processing Payment",Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton("Cancel Booking") { dialog, id ->
                                // Dismiss the dialog
                                dialog.dismiss()
                                var intent = Intent(this, ManageBookingTGActivity::class.java)
                                startActivity(intent)
                            }
                        val alert = builder.create()
                        alert.show()

                    }.addOnFailureListener{
                        //when saving failed
                        Toast.makeText(this,"Saving Failed",Toast.LENGTH_SHORT).show()
                    }
                } else{
                    Toast.makeText(this,"Saving Failed-No User",Toast.LENGTH_SHORT).show()
                }

            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editTextDateToTG.showSoftInputOnFocus = false
            editTextDateFromTG.showSoftInputOnFocus = false
        } else {
            try {
                val method: Method = EditText::class.java.getMethod(
                    "setShowSoftInputOnFocus", *arrayOf<Class<*>?>(Boolean::class.javaPrimitiveType)
                )
                method.isAccessible = true
                method.invoke(editTextDateToTG, false)
                method.invoke(editTextDateFromTG, false)
            } catch (e: java.lang.Exception) {
                // ignore
            }
        }
    }


    private fun getDateDiff(format: SimpleDateFormat, oldDate: String, newDate: String): Long {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val dateFrom = sdf.parse(oldDate)
            val toFrom = sdf.parse(newDate)
            val diff: Long = toFrom.time - dateFrom.time
            TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}