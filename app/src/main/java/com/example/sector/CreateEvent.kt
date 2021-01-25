package com.example.sector

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_event.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.YEAR
import javax.xml.datatype.DatatypeConstants.MONTHS

/*
    Class for creation of events, makes use of a spinner for job type selection to prevent free input and create identifiers by type for filers
    uses date & time pickers for ease of input
    save triggers firebase function to convert input into data and store in backend database

    To-Do
        - Reduce Date & Time picker & update func code to remove duplicates
            - Combine Date & Time?
        - Export firebase functionality to seperate class and implement Event data class
        - add if successful listener to create event & toast for user feedback to confirm event creation
 */

private val firebaseQuery: FirebaseQuery = FirebaseQuery()
private const val TAG = "tag"

class CreateEvent : AppCompatActivity(){

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        val saveButton = findViewById<View>(R.id.create_event_button) as Button
        val eventId = ""

        saveButton.setOnClickListener {
            createEvent(eventId, job_type, venue_name, start_date,start_time,end_date,end_time,number_of_staff)
        }

        start_date!!.setOnClickListener {
            val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateStartDate()
                }
            }
            DatePickerDialog(
                this@CreateEvent,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        end_date!!.setOnClickListener {
            val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateEndDate()
                }
            }
            DatePickerDialog(
                this@CreateEvent,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        start_time!!.setOnClickListener {
            val timeSetListener = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hour: Int, min: Int) {
                    cal.set(Calendar.HOUR, hour)
                    cal.set(Calendar.MINUTE, min)
                    updateStartTime()
                }
            }
            TimePickerDialog(
                this@CreateEvent,
                timeSetListener,
                cal.get(Calendar.HOUR),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        end_time!!.setOnClickListener {
            val timeSetListener = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hour: Int, min: Int) {
                    cal.set(Calendar.HOUR, hour)
                    cal.set(Calendar.MINUTE, min)
                    updateEndTime()
                }
            }
            TimePickerDialog(
                this@CreateEvent,
                timeSetListener,
                cal.get(Calendar.HOUR),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }


        //Create drop down list for Job type Selection
        // Array stored in Strings.xml
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.job_type_list, android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        job_type.adapter = adapter
    }


    private fun createEvent(eventId : String, jobType : Spinner, venueName : TextView, startDate : EditText, startTime : EditText,endDate : EditText, endTime : EditText, noOfStaff : EditText) {
        val jobType = jobType.selectedItem.toString()
        val venueName = venueName.text.toString()
        val noOfStaff = noOfStaff.text.toString()
        val intNoOfStaff = Integer.parseInt(noOfStaff)

        val startDate = startDate.text.toString()
        //val finalStartDate = Date.parse(startDate)
        val startTime = startTime.text.toString()
       // val finalStartTime = Time.parse(startTime)

        val endDate = endDate.text.toString()
       // val finalEndDate = Date.parse(endDate)
        val endTime = endTime.text.toString()
       // val finalEndTime = Time.parse(endTime)


        //val dateSelect = dateSelect.text.toString()
        //val dateSelect = dateSelect.text.toString()
        //val startTime = startTime.text.toString()
        //val endTime = endTime.text.toString()

        //Append start date & time, end Date & Time into dateTime date format to be stored together in database



        Event(eventId,jobType,venueName,startDate,startTime,endDate,endTime,intNoOfStaff)
        //Event(jobType,venueName,finalStartDate,finalStartTime,finalEndDate,finalEndTime,intNoOfStaff)

        val newEvent = Event(eventId,jobType,venueName,startDate,startTime,endDate,endTime,intNoOfStaff)
        //Add unique generated ID to event document
        firebaseQuery.firebaseDb.collection("Events").add(newEvent)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                firebaseQuery.firebaseDb.collection("Events").document(documentReference.id)
                    .update("eventId", documentReference.id)
                startActivity(Intent(this, MainActivity::class.java))
            }

    }

    private fun updateStartDate() {
        //Date date
        val myFormat = "EEE, MMM d, yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        start_date.setText(sdf.format(cal.getTime()))

    }
    private fun updateEndDate() {
        val myFormat = "EEE, MMM d, yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        end_date.setText(sdf.format(cal.getTime()))

    }

    private fun updateStartTime() {
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        start_time.setText(sdf.format(cal.getTime()))

    }

    private fun updateEndTime() {
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        end_time.setText(sdf.format(cal.getTime()))
    }

   /* fun getDateFromString(date): String{
        Date date = format.parse(date)
    }*/
}




