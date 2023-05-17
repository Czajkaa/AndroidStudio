package com.example.projekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.slider.Slider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Patient_RegisterNext : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_register_next)

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser
        var email = user?.email
        var userID = nickname

        val inputName = findViewById<EditText>(R.id.patient_register_page2_name)
        val rangeSlider = findViewById<Slider>(R.id.patient_register_page2_slider)
        val Spinner = findViewById<Spinner>(R.id.patient_register_page2_spinner)


        val button = findViewById<Button>(R.id.patient_register_page2_button_next)
        button.setOnClickListener {
            if (email != null) {
                userData(userID, email, inputName, rangeSlider, Spinner)
            }
            Toast.makeText(baseContext, "Registration success", Toast.LENGTH_SHORT,).show()
            var login = Intent(applicationContext, Patient_Login::class.java)
            startActivity(login)
        }
    }

    private fun userData(userID: String, email: String, inputName: EditText, rangeSlider: Slider, Spinner: Spinner) {
        val name: String = inputName?.text.toString().trim() {it <= ' '}
        val age = rangeSlider.value.toInt()
        val gender = Spinner.selectedItem.toString()

        var database = FirebaseDatabase.getInstance("https://account-dfca5-default-rtdb.europe-west1.firebasedatabase.app").reference.child("UserID").child(userID)
        var users = Users(email, name, age, gender)
        database.setValue(users)
    }

    override fun onBackPressed() {
    }
}