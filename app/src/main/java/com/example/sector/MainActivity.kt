package com.example.sector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var signUpButton : Button
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        signUpButton = findViewById(R.id.sign_up_button)
        signUpButton.setOnClickListener{
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }

        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener{
           loginUser()
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    //Login method checking for empty text fields functions and a function to pass username and password to firebase authentication
    private fun loginUser(){
        if (username.text.toString().trim().isEmpty()){
            username.error = "Please enter an email address"
            username.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()){
            username.error = "Please enter a valid email address"
            username.requestFocus()
            return
        }
        if (password.text.toString().trim().isEmpty()){
            password.error = "Please enter a Password"
            password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    updateUI(null)
                }
            }
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
