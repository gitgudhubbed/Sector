package com.example.sector

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_account.*


class CreateAccount : AppCompatActivity() {

    private lateinit var database: DocumentReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        auth = FirebaseAuth.getInstance()
        //val db = FirebaseFirestore.getInstance()

        val saveButton = findViewById<View>(R.id.sign_up) as Button

        saveButton.setOnClickListener {
            saveUser()
        }
    }

    public override fun onStart() {
        super.onStart()
    }


    //Function to capture required personal details for firebase authentication and creation of corresponding "User" field in "Users" collection
    private fun saveUser() {
        val db = FirebaseFirestore.getInstance()

        val firstName = create_first_name.text.toString()
        val surname = create_surname.text.toString()
        val email = create_username.text.toString()
        val address = create_postal_address.text.toString()

        val userProfileInfo = hashMapOf<String, Any>(
            "firstName" to firstName,
            "surname" to surname,
            "emailAddress" to email,
            "postalAddress" to address
        )


        if (create_username.text.toString().trim().isEmpty()) {
            create_username.error = "Please enter an email address"
            create_username.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(create_username.text.toString()).matches()) {
            create_username.error = "Please enter a valid email address"
            create_username.requestFocus()
            return
        }
        if (create_password.text.toString().trim().isEmpty()) {
            create_password.error = "Please enter a Password"
            create_password.requestFocus()
            return
        }
        if (create_postal_address.text.toString().trim().isEmpty()) {
            create_postal_address.error = "Please enter a postal address"
            create_postal_address.requestFocus()
            return
        }

            //Firebase authentification function then creating an entry into the linked firestore database using the new users unique UID to hold relevant personal info of user
            // - populated from a HashMap using the input data
            auth.createUserWithEmailAndPassword(
                create_username.text.toString(),
                create_password.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        var user = auth.currentUser
                        var userID = user!!.uid
                        db.collection("Users").document(userID).set(userProfileInfo)
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Sign up failed please try again in a few minutes",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


        }
    }
