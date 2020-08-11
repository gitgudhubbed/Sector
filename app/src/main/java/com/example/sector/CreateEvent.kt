package com.example.sector

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_event.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class CreateEvent : AppCompatActivity() {

    private lateinit var dateSelection : EditText
    private lateinit var timeSelection : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        val saveButton = findViewById<View>(R.id.create_event_button) as Button

        saveButton.setOnClickListener {
            saveEvent()
        }

        //Create drop down list for Job type Selection
        // Create an ArrayAdapter
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.job_type_list, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        job_type.adapter = adapter


        //Create a Datepicker on screen when date_select EditText is pressed
        dateSelection = findViewById(R.id.date_select)
        dateSelection.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, dayOfMonth, monthOfYear ->
                    // Display Selected date in textbox
                    date_select.setText("" + dayOfMonth + ", " + monthOfYear + ", " + year)
                }, year, month, day
            )
            dpd.show()
        }

        //Create a Time picker on screen when Edit text view is selected
        timeSelection = findViewById(R.id.start_time)
        timeSelection.setOnClickListener {
            val t = Calendar.getInstance()
            val hour = t.get(Calendar.HOUR)
            val minute = t.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    start_time.setText("" + hourOfDay + ":" + "" + minute)
                }, hour, minute, false
            )
            tpd.show()
        }

        //Create a time picker on screen when end time Edit Text view is selected
        timeSelection = findViewById(R.id.end_time)
        timeSelection.setOnClickListener {
            val t = Calendar.getInstance()
            val hour = t.get(Calendar.HOUR)
            val minute = t.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    end_time.setText("" + hourOfDay + ":" + "" + minute)
                }, hour, minute, false
            )
            tpd.show()
        }

    }

    private fun saveEvent(){

        var db = FirebaseFirestore.getInstance()

        val jobTypeText = findViewById<View>(R.id.job_type) as Spinner
        val venueNameText = findViewById<View>(R.id.venue_name) as EditText
        val dateSelectText = findViewById<View>(R.id.date_select) as EditText
        val StartTimeText = findViewById<View>(R.id.start_time) as EditText
        val EndTimeText = findViewById<View>(R.id.end_time) as EditText
        val NumberStaffText = findViewById<View>(R.id.number_of_staff) as EditText

        val jobType = jobTypeText.selectedItem.toString()
        val venueName = venueNameText.text.toString().trim()
        val dateSelect = dateSelectText.text.toString().trim()
        val startTime = StartTimeText.text.toString().trim()
        val EndTime = EndTimeText.text.toString().trim()
        val NumberStaff = NumberStaffText.text.toString().trim()

        if(jobType.isNotEmpty() && venueName.isNotEmpty() && dateSelect.isNotEmpty() && startTime.isNotEmpty() && EndTime.isNotEmpty() && NumberStaff.isNotEmpty()){
            try{
                val eventInfo = hashMapOf<Any, Any>(
                    "jobType" to jobType,
                    "venueName" to venueName,
                    "eventDate" to dateSelectText,
                    "eventStartTime" to StartTimeText,
                    "eventEndTime" to EndTimeText,
                    "NumberOfStaff" to NumberStaff
                )
               // val event = HashMap<String,Any>()
              //  event.put("Job Type", jobType)
               // event.put("Venue Name", venueName)
               // event.put("Event Date", dateSelect)
               // event.put("Event Start Time", startTime)
               // event.put("Event End Time", EndTime)
               // event.put("Number of Staff", NumberStaff)

                db.collection("Events").add(eventInfo).addOnSuccessListener {
                    Toast.makeText(this,"success",Toast.LENGTH_LONG).show()
                }.addOnFailureListener { exception: java.lang.Exception ->
                    Toast.makeText(this,exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch(e:Exception) {
                Toast.makeText(this,e.toString(), Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(applicationContext,"Missing Field, please re-enter", Toast.LENGTH_SHORT).show()
        }

    }
}
