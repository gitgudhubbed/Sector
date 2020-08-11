package com.example.sector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_event_dashboard.*

class EventDashboard : AppCompatActivity() {
    private lateinit var eRecyclerView: RecyclerView
    //private lateinit var db : DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_dashboard)


        eRecyclerView = findViewById(R.id.event_list)
        val db = FirebaseFirestore.getInstance()

        // eventAdapter()

    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}

private fun eventAdpater() {
    var FirestoreRecyclerAdapter = object : FirestoreRecyclerAdapter<Event, EventDashboard.EventViewHolder> {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventDashboard.EventViewHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(holder: EventDashboard.EventViewHolder, position: Int, model: Event) {
           val v = LayoutInflater.from()
        }

    }
}







