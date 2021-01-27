package com.example.sector

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*

class FirebaseQuery {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseDb: FirebaseFirestore = FirebaseFirestore.getInstance()


    //Authentification
    fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
        //Login Auth
    fun loginUser(username: TextView, password: TextView): Task<AuthResult> {
        if (username.text.toString().trim().isEmpty()) {
            username.error = "Please enter an email address"
            username.requestFocus()
            //return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            username.error = "Please enter a valid email address"
            username.requestFocus()
            //return
        }
        if (password.text.toString().trim().isEmpty()) {
            password.error = "Please enter a Password"
            password.requestFocus()
            //return
        }

        return firebaseAuth.signInWithEmailAndPassword(
            username.text.toString(),
            password.text.toString()
        )
    }
        //Create Auth
    fun createUserAuth(username: TextView,password: TextView): Task<AuthResult> {
        if (username.text.toString().trim().isEmpty()) {
            username.error = "Please enter an email address"
            username.requestFocus()
            //return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            username.error = "Please enter a valid email address"
            username.requestFocus()
            //return
        }
        if (password.text.toString().trim().isEmpty()) {
            password.error = "Please enter a Password"
            password.requestFocus()
            //return
        }
        return firebaseAuth.createUserWithEmailAndPassword(
            username.text.toString(),
            password.text.toString()
        )
    }
}