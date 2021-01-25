package com.example.sector

import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.android.synthetic.main.event_item.view.*

/*
    Class to convert data from firestore database into organised views contained within a viewholder
    - Implemented onclick listener to expends events on click to display all information
        - Implement auth levels for allowing creation & Editing of events
 */

private const val TAG = "tag"
private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
val firebaseDb: FirebaseFirestore = FirebaseFirestore.getInstance()


class EventAdapter (var eventListItems: List<Event>, val clickListener : (Event) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var linearLayout : LinearLayout = itemView.findViewById(R.id.linearLayout)
        var expandableLayout : RelativeLayout = itemView.findViewById(R.id.expandable_layout)
        var applyBtn : Button = itemView.findViewById(R.id.apply_to_event)


        fun bind (event : Event, clickListener: (Event) -> Unit) {
            itemView.job_type.text = event.jobType
            itemView.venue_name.text = event.venueName

            //Hidden/expandable Views
            itemView.start_date.text = event.startDate
            //itemView.start_date.setText("Start Date" + event.startDate)
            itemView.start_time.text = event.startTime
            itemView.end_date.text = event.endDate
            itemView.end_time.text = event.endTime
            itemView.no_of_staff.text = event.noOfStaff.toString()

            //Remove sharp corners from layout
            //linearLayout.clipToOutline = true

            itemView.setOnClickListener{
                    clickListener(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
       return eventListItems.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).bind(eventListItems[position], clickListener)
        val context = holder.itemView.context

        //Expandable view bind
        val isExpandable : Boolean = eventListItems[position].expandable
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            val event = eventListItems[position]
            event.expandable = !event.expandable
            notifyItemChanged(position)
        }

        holder.applyBtn.setOnClickListener{
            val event = eventListItems[position]
            val eventId = event.eventId
            Log.d(TAG, "applying to event: ${eventId}")
            val userId = getUser()!!.uid
            Log.d(TAG, "user apply for event: ${userId}")
            firebaseDb.collection("Users").document(userId).update("userEvents", FieldValue.arrayUnion(eventId))
                .addOnCompleteListener{
                    Toast.makeText(context,"Successful application", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }


}
