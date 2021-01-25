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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.common.collect.ArrayListMultimap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.event_item.*
import java.util.*
import kotlin.collections.ArrayList

private val firebaseQuery: FirebaseQuery = FirebaseQuery()
private lateinit var functions: FirebaseFunctions
private const val TAG = "tag"

class MyProfile : AppCompatActivity() {
    //Get instance of database & current user
    private val userID = firebaseQuery.getUser()!!.uid
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var eventList : List<Event> = ArrayList()
    //private val userEventAdapter : UserProfileAdapter = userEventAdapter(eventList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        functions = Firebase.functions


        //Populate text fields with data stored in firebase using unique user ID
        val docRef = db.collection("Users").document(userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG,"exists DocumentSnapshot data: ${document.data}")
                    Log.d(TAG,"exists DocumentSnapshot data: ${docRef}")
                    Log.d(TAG,"exists DocumentSnapshot data: ${userID}")
                    user_first_name.text = document.getString("firstName")
                    user_surname.text = document.getString("surname")
                    user_email_address.text = document.getString("emailAddress")
                    //address.text = document.getString("postalAddress")
                    getUserEventList().addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            val e = task.exception
                            if (e is FirebaseFunctionsException) {
                                val code = e.code
                                val details = e.details
                                Log.d(TAG,"code error: ${code}")
                                Log.d(TAG,"error details: ${details}")
                            } else {
                                Log.d(TAG, "task exception ${e}")
                            }
                            // ...
                        } else if(task.isSuccessful){
                            Log.d(TAG,"No error")
                            val eventList = task.result!!
                            val listSize = eventList.size
                            Log.d(TAG, "User event list ${eventList}")
                            Log.d(TAG, "User event list ${listSize}")
                            //iterate through result list
                            val eventListIterator = eventList.iterator()
                            eventList.forEach{
                                //it.data.toObject(Event::class.java)
                                //it.elementAt(1)
                                //userEventAdapter.userEventListItems = eventList
                                //userEventAdapter.notifyDataSetChanged()
                                Log.d(TAG, "User event items ${it}")

                            }
                        }

                        // ...
                    })

                } else {
                    Log.d("Doesn't  exist", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }

        //Expandable view control - Personal details
        personal_detail_linearLayout.setOnClickListener {
            if(user_profile_expandable_layout.visibility == View.GONE){
                //TransitionManager.beginDelayedTransition(LinearLayout,AutoTransition)
                user_profile_expandable_layout.visibility = View.VISIBLE
            } else{
                user_profile_expandable_layout.visibility = View.GONE
            }
        }

        //Expandable view control - User events
        user_event_linearLayout.setOnClickListener {
            if(user_event_expandable_layout.visibility == View.GONE){
                //TransitionManager.beginDelayedTransition(LinearLayout,AutoTransition)
                user_event_expandable_layout.visibility = View.VISIBLE
            } else{
                user_event_expandable_layout.visibility = View.GONE
            }
        }

    }

    private fun getUserEventList() : Task< ArrayList<Any>> {
        val data = userID
        Log.d(TAG,"Current user ${data}")
        //Log.d(TAG,"Current user uId ${uId}")

        return functions
            .getHttpsCallable("getEvents_v0")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                @Suppress("UNCHECKED_CAST")
                //val result = task.result?.data as Map<DocumentSnapshot, DocumentSnapshot>
                val result = task.result?.data as ArrayList<Any>
                Log.d(TAG, "User events ${result}")
                result
            }
    }

}

