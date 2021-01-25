package com.example.sector

import com.google.firebase.Timestamp

//Data class to store raw data from database into fields to then be inserted into views

data class Event(
    val eventId : String = "",
    val jobType: String = "",
    val venueName: String = "",
    val startDate : String = "",
    val startTime : String = "",
    val endDate : String = "",
    val endTime : String = "",
    val noOfStaff : Int = 0,
    var expandable : Boolean = false
    )



//val startDate: Timestamp? = null,
//val endDate: Timestamp? = null
