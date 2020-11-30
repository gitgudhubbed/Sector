package com.example.sector

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
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
        var linearLayout : LinearLayout = itemView.findViewById(R.id.linearLayout)
        var expandableLayout : RelativeLayout = itemView.findViewById(R.id.expandable_layout)

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

    // If using multiple view types
    //override fun getItemViewType(position: Int): Int {
     //   return super.getItemViewType(position)
   // }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).bind(eventListItems[position], clickListener)

        //Expandable view bind
        val isExpandable : Boolean = eventListItems[position].expandable
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            val event = eventListItems[position]
            event.expandable = !event.expandable
            notifyItemChanged(position)
        }

    }

}
