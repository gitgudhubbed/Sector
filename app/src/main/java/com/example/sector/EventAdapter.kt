package com.example.sector

import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter

class EventAdapter: FirestoreRecyclerAdapter<,EventDashboard.EventViewHolder>() {

    class EventHolder extends RecyclerView.ViewHolder{

    }
}