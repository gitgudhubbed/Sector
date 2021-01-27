package com.example.sector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG: String = "LOGIN_PAGE_LOG"

class MainActivity : AppCompatActivity() {

    private val firebaseQuery: FirebaseQuery = FirebaseQuery()

    private lateinit var signUpButton : Button
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signUpButton = findViewById(R.id.sign_up_button)
        signUpButton.setOnClickListener{
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }

        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener{
           firebaseQuery.loginUser(username,password)
               .addOnCompleteListener {
                   if(it.isSuccessful){
                       Log.d(TAG, "sign-in success")
                       //val user = firebaseQuery.firebaseAuth.currentUser
                       //firebaseQuery.getUser() = auth.currentUser
                       updateUI(firebaseQuery.getUser())
                   }else {
                       Log.d(TAG, "Error: ${it.exception!!.message}")
                   }
               }
        }

       // if(firebaseQuery.getUser() != null){
       //     updateUI(firebaseQuery.getUser())
       //     Log.d(TAG, "sign-in success")
      //  } else {
            //Login user
      //      updateUI(null)
            //firebaseQuery.loginUser(username,password)
        //}
    }

    //If a user is logged in thus not null they will be navigated to the dashboard activity, else are held at login with an failure toast
    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null){
            startActivity(Intent(this, DashboardActivity::class.java))
        }else{
            Toast.makeText(baseContext, "Login failed", Toast.LENGTH_SHORT).show()
        }

    }
}
