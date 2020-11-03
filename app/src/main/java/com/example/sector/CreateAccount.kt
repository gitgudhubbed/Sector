package com.example.sector

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*

private val firebaseQuery: FirebaseQuery = FirebaseQuery()


class CreateAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)


        val saveButton = findViewById<View>(R.id.sign_up) as Button
        saveButton.setOnClickListener {
            createUser(create_username,create_password,create_first_name,create_surname)
        }
    }

    //Function to create user, passing firebase authentifcation to query class and using User data class to create user object passing in submitted values, combining object and auth to write to database
    private fun createUser(username:TextView,password: TextView, firstName : TextView, surname : TextView) {
        val userFirstname = firstName.text.toString()
        val userSurname = surname.text.toString()
        val userEmail = username.text.toString()
        User(userFirstname, userSurname, userEmail)

        firebaseQuery.createUserAuth(username, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val newUser = User(userFirstname, userSurname, userEmail)
                    //Returns firestore generated unique ID for database storage
                    val userID = firebaseQuery.getUser()!!.uid
                    firebaseQuery.firebaseDb.collection("Users").document(userID).set(newUser)
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Sign up failed please try again in a few minutes", Toast.LENGTH_LONG).show()
                }
            }
    }
}











