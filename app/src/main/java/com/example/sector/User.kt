package com.example.sector

import java.util.*

/*
    To Do
     - Implement assigned auth level to user
     - Implement user/Event link
 */

data class User(
    val firstName : String = "",
    val surname : String = "",
    val emailAddress : String = "",
    val userEvents : Array <Objects>
)