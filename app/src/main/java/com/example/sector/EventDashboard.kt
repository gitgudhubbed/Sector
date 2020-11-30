package com.example.sector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_event_dashboard.*
import kotlinx.android.synthetic.main.event_item.*
import kotlinx.android.synthetic.main.event_item.view.*
import java.lang.reflect.Array.get

/* On launch of event activity get list of all events from database & Convert into displayable information
    TO-DO
        - only display events assigned to user? allow applications via this list?
*/
class EventDashboard : AppCompatActivity(), (Event) -> Unit {

    private var eventList : List<Event> = ArrayList()
    private val eventAdapter : EventAdapter = EventAdapter(eventList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_dashboard)

        //Retrieve instance of database
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        /*apply_to_event!!.setOnClickListener {
            saveUserEvent()
        }*/

        //Pull Event list from Database & sort by job type
        fun getEventList() : Task<QuerySnapshot> {
            return db
                .collection("Events")
                .orderBy("jobType", Query.Direction.DESCENDING)
                .get()
        }

        //init recycler view
        event_recycler.layoutManager = LinearLayoutManager(this)
        event_recycler.adapter = eventAdapter
        //event_recycler.clipToOutline = true


        //Load event list into View holder
        fun loadEventList(){
            getEventList().addOnCompleteListener {
                if(it.isSuccessful){
                    eventList = it.result!!.toObjects(Event::class.java)
                    eventAdapter.eventListItems = eventList
                    eventAdapter.notifyDataSetChanged()
                } else{
                    Log.d("tag", "Error: ${it.exception!!.message}")
                }
            }
        }
        //Execute function
        loadEventList()
    }

    //Event trigger for clicking on event items
    override fun invoke(event: Event) {
        //Make full event info visable

       Toast.makeText(this, "Clicked event ${event.venueName}", Toast.LENGTH_LONG).show()
    }

    /*private fun saveUserEvent(){
        val eUid = get(DocumentId)
    }*/
}





