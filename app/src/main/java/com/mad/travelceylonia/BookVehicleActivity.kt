package com.mad.travelceylonia

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class BookVehicleActivity : AppCompatActivity() {

    lateinit var selectedVehicle : DataClass
    private lateinit var database: DatabaseReference

    private lateinit var context : Context

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_vehicle)

        database = FirebaseDatabase.getInstance().getReference("Bookings")

        context = this

        val vehicle = intent.getParcelableExtra<DataClass>("vehicle")
        if(vehicle != null){

            selectedVehicle = vehicle
            val vehiName: TextView = findViewById(R.id.vehicleName)
            vehiName.text = vehicle.dataTitle

        }

        val cost: TextView = findViewById(R.id.costTxt)
        val fromDate: EditText = findViewById(R.id.editTextDateFrom)

        fromDate.setOnClickListener {
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
                    fromDate.setText((dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year))
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


        val toDate: EditText = findViewById(R.id.editTextDateTo)

        toDate.setOnClickListener {
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
                    toDate.setText((dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year))
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

        var calc : Button = findViewById<Button>(R.id.calculateCostButton)

        calc.setOnClickListener{

            if(fromDate.text.toString().isEmpty() || toDate.text.toString().isEmpty()){
                Toast.makeText(this,"Please Select Date Range", Toast.LENGTH_SHORT).show()
            }else{

                val dateDifference =
                    getDateDiff(SimpleDateFormat("dd/MM/yyyy"), fromDate.text.toString(), toDate.text.toString()) + 1

                val totalCost = selectedVehicle.dataCost * dateDifference
                cost.text = "Total Cost : $$totalCost";
                //Toast.makeText(this, "Date Diff $$totalCost",Toast.LENGTH_SHORT).show()
            }

        }


        var conf : Button = findViewById<Button>(R.id.confirmButton)

        conf.setOnClickListener{

            if(fromDate.text.toString().isEmpty() || toDate.text.toString().isEmpty()){
                Toast.makeText(this,"Please Select Date Range", Toast.LENGTH_SHORT).show()
            } else {
                val dateDifference =
                    getDateDiff(SimpleDateFormat("dd/MM/yyyy"), fromDate.text.toString(), toDate.text.toString()) + 1
                val totalCost = selectedVehicle.dataCost * dateDifference

                val user = Firebase.auth.currentUser
                if (user != null) {
                    var booking = BookingData(selectedVehicle.dataTitle+" - "+selectedVehicle.id,fromDate.text.toString() , toDate.text.toString(),dateDifference,totalCost,user.email.toString(),selectedVehicle.dataCost.toInt(),"no")

                    //save record
                    database.push().setValue(booking).addOnSuccessListener {
                        //when successfully saved

                        val intent = Intent(context, BookingDetailsActivity::class.java)
                        intent.putExtra("booking", booking)
                        startActivity(intent)

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
            toDate.showSoftInputOnFocus = false
            fromDate.showSoftInputOnFocus = false
        } else {
            try {
                val method: Method = EditText::class.java.getMethod(
                    "setShowSoftInputOnFocus", *arrayOf<Class<*>?>(Boolean::class.javaPrimitiveType)
                )
                method.isAccessible = true
                method.invoke(toDate, false)
                method.invoke(fromDate, false)
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