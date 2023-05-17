package com.example.projekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Patient_Start : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_start)

        val button_patient_login = findViewById<Button>(R.id.patient_page_button_login)
        button_patient_login.setOnClickListener {
            var patient_log_in_page = Intent(applicationContext, Patient_Login::class.java)
            startActivity(patient_log_in_page)
        }

        val button_patient_register = findViewById<Button>(R.id.patient_page_button_register)
        button_patient_register.setOnClickListener {
            var patient_log_in_page = Intent(applicationContext, Patient_Register::class.java)
            startActivity(patient_log_in_page)
        }
    }
}