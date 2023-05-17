package com.example.projekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class Patient_Welcome : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_welcome)

        auth = FirebaseAuth.getInstance()
        var text_user = findViewById<TextView>(R.id.patient_welcome_page_text2)
        var user = auth.currentUser
        if(user == null) {
            text_user.text = "Guest."
        } else {
            text_user.text = user.email
        }
    }

    override fun onBackPressed() {
    }
}