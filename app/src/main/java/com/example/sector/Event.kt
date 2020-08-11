package com.example.sector

class Event(title: String, desc: String){

    val title : String
    val desc : String
    //lateinit var date : String

    //constructor() : this() {
        //Empty constructor
    //}

    init {
        this.title = title
        this.desc = desc
    }

}