package com.mad.travelceylonia

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

class EditTGBookingActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tgbooking)

        database = FirebaseDatabase.getInstance().getReference("GuideBookings")

        val nameEdit: TextView = findViewById(R.id.guideNameEdit)
        val dateFromEdit: TextView = findViewById(R.id.editTextDateFromEditTGU)
        val dateToEdit: TextView = findViewById(R.id.editTextDateToEditTGU)
        val costEdit: TextView = findViewById(R.id.costTxtEditTGU)
        var update : Button = findViewById<Button>(R.id.updateBookingButtonTGU)
        var calc : Button = findViewById<Button>(R.id.calculateCostButtonEditTGU)

        dateFromEdit.setOnClickListener {
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
                    dateFromEdit.setText((dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year))
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

        dateToEdit.setOnClickListener {
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
                    dateToEdit.setText((dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year))
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

        val booking = intent.getParcelableExtra<ManageGuideBookingData>("booking")
        if(booking != null){

            nameEdit.text = booking.guide
            dateFromEdit.text = booking.fromDate
            dateToEdit.text = booking.toDate
            costEdit.text = "Total Cost : $"+booking.totalCost

            update.setOnClickListener{

                if(dateFromEdit.text.toString().isEmpty() || dateToEdit.text.toString().isEmpty()){
                    Toast.makeText(this,"Please Select Date Range", Toast.LENGTH_SHORT).show()
                }else{
                    val dateDifference = getDateDiff(SimpleDateFormat("dd/MM/yyyy"), dateFromEdit.text.toString(), dateToEdit.text.toString()) + 1
                    val cost = booking.costPerDay
                    val totalCost = cost * dateDifference

                    val user = Firebase.auth.currentUser
                    if (user != null) {
                        var bookings = GuideBookingData(booking.guide,dateFromEdit.text.toString() , dateToEdit.text.toString(),cost,dateDifference.toInt(),totalCost.toInt(),"no",user.email.toString())

                        //save record
                        database.child(booking.id).setValue(bookings).addOnSuccessListener {
                            //when successfully saved
                            val intent = Intent(this, ManageBookingTGActivity::class.java)
                            startActivity(intent)
                        }.addOnFailureListener{
                            //when saving failed
                            Toast.makeText(this,"Saving Failed", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(this,"Saving Failed-No User", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            calc.setOnClickListener{
                if(dateFromEdit.text.toString().isEmpty() || dateToEdit.text.toString().isEmpty()){
                    Toast.makeText(this,"Please Select Date Range", Toast.LENGTH_SHORT).show()
                }else{
                    val dateDifference = getDateDiff(SimpleDateFormat("dd/MM/yyyy"), dateFromEdit.text.toString(), dateToEdit.text.toString()) + 1
                    val totalCost = booking.costPerDay * dateDifference
                    costEdit.text = "Total Cost : $$totalCost";

                }

            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dateToEdit.showSoftInputOnFocus = false
            dateFromEdit.showSoftInputOnFocus = false
        } else {
            try {
                val method: Method = EditText::class.java.getMethod(
                    "setShowSoftInputOnFocus", *arrayOf<Class<*>?>(Boolean::class.javaPrimitiveType)
                )
                method.isAccessible = true
                method.invoke(dateToEdit, false)
                method.invoke(dateFromEdit, false)
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