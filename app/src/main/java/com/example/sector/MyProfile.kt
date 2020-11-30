package com.example.sector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.event_item.*

private val firebaseQuery: FirebaseQuery = FirebaseQuery()

class MyProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        //Get instance of database & current user
        val userID = firebaseQuery.getUser()!!.uid
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        //Populate text fields with data stored in firebase using unique user ID
        val docRef = db.collection("Users").document(userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("exists", "DocumentSnapshot data: ${document.data}")
                    user_first_name.text = document.getString("firstName")
                    user_surname.text = document.getString("surname")
                    user_email_address.text = document.getString("emailAddress")
                    //address.text = document.getString("postalAddress")

                } else {
                    Log.d("Doesn't  exist", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }

        //Expandable view control
        personal_detail_linearLayout.setOnClickListener {
            if(user_profile_expandable_layout.visibility == View.GONE){
                //TransitionManager.beginDelayedTransition(LinearLayout,AutoTransition)
                user_profile_expandable_layout.visibility = View.VISIBLE
            } else{
                user_profile_expandable_layout.visibility = View.GONE
            }
        }

    }
}
