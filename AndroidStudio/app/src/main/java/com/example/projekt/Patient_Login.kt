package com.example.projekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Patient_Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_login)

        auth = Firebase.auth
        val button = findViewById<Button>(R.id.patient_login_page_button_login)
        val inputEmail = findViewById<EditText>(R.id.patient_login_page_email)
        val inputPassword = findViewById<EditText>(R.id.patient_login_page_password)
        button.isEnabled = true

        button.setOnClickListener {
            val login: String = inputEmail?.text.toString().trim() {it <= ' '}
            val password: String = inputPassword?.text.toString().trim() {it <= ' '}
            loginAccount(login,password)
            inputEmail.text.clear()
            inputPassword.text.clear()
        }
    }

    private fun loginAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                Toast.makeText(baseContext, "Authentication success.", Toast.LENGTH_SHORT).show()
                val user = auth.currentUser
                updateUI(user)
                var welcome_page = Intent(applicationContext, Patient_Welcome::class.java)
                startActivity(welcome_page)
            } else {
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    override fun onBackPressed() {
        var login = Intent(applicationContext, Patient_Start::class.java)
        startActivity(login)
    }
}