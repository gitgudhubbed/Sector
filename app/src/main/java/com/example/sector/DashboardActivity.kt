package com.example.sector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var myProfileButton: Button
    private lateinit var myEventButton: Button
    private lateinit var createEventButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //temp location
        createEventButton = findViewById(R.id.create_event_button)
        createEventButton.setOnClickListener {
            val intent = Intent(this, CreateEvent::class.java)
            startActivity(intent)
        }

        myEventButton = findViewById(R.id.event_Button)
        myEventButton.setOnClickListener {
            val intent = Intent(this, EventDashboard::class.java)
            startActivity(intent)
        }

        myProfileButton = findViewById(R.id.my_profile_button)
        myProfileButton.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }
    }
}
