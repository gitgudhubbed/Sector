package com.example.sector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class MyProfile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    //private lateinit var db: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        var user = auth.currentUser
        var userID = user!!.uid

        val firstName = findViewById<TextView>(R.id.user_first_name)
        val surname = findViewById<TextView>(R.id.user_surname)
        val email = findViewById<TextView>(R.id.user_email_address)
        val address = findViewById<TextView>(R.id.user_postal_address)

        val docRef = db.collection("Users").document(userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("exists", "DocumentSnapshot data: ${document.data}")
                    firstName.text = document.getString("firstName")
                    surname.text = document.getString("surname")
                    email.text = document.getString("emailAddress")
                    address.text = document.getString("postalAddress")

                } else {
                    Log.d("no exist", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }

    }
}
