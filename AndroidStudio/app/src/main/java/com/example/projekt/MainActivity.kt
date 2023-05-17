package com.example.projekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_doctor = findViewById<Button>(R.id.start_page_button_doctor)
        button_doctor.setOnClickListener {
            var doctor_login_page = Intent(applicationContext, Doctor_Start::class.java)
            startActivity(doctor_login_page)
        }

        val button_patient = findViewById<Button>(R.id.start_page_button_patient)
        button_patient.setOnClickListener {
            var patient_login_page = Intent(applicationContext, Patient_Start::class.java)
            startActivity(patient_login_page)
        }
    }

    override fun onBackPressed() {
    }
}