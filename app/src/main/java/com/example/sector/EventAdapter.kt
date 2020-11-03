package com.example.sector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import kotlinx.android.synthetic.main.event_item.view.*

/*
    Class to convert data from firestore database into organised views contained within a viewholder
    - Implemented onclick listener to expends events on click to display all information
        - Implement auth levels for allowing creation & Editing of events
 */

class EventAdapter (var eventListItems: List<Event>, val clickListener : (Event) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind (event : Event, clickListener: (Event) -> Unit) {
            itemView.job_type.text = event.jobType
            itemView.venue_name.text = event.venueName

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

    // If using multiple view types
    //override fun getItemViewType(position: Int): Int {
     //   return super.getItemViewType(position)
   // }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).bind(eventListItems[position], clickListener)
    }

}
