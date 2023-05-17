package com.example.projekt

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase

var nickname: String = ""

class Patient_Register : AppCompatActivity() {

    private var check1: Boolean = false
    private var check2: Boolean = false
    private var check3: Boolean = false
    private var check4: Boolean = false
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_register)

        auth = Firebase.auth
        val button = findViewById<Button>(R.id.patient_register_page_button_next)
        val inputEmail = findViewById<EditText>(R.id.patient_register_page_email)
        val inputPassword = findViewById<EditText>(R.id.patient_register_page_password)
        val inputNickname = findViewById<EditText>(R.id.patient_register_page_nickname)
        button.isEnabled = false

        button.setOnClickListener {
            val login: String = inputEmail?.text.toString().trim() {it <= ' '}
            val password: String = inputPassword?.text.toString().trim() {it <= ' '}
            nickname = inputNickname?.text.toString().trim() {it <= ' '}

            nicknameValidation(login, password)

            inputEmail.text.clear()
            inputNickname.text.clear()
            inputPassword.text.clear()
        }

        inputChanged()
    }

    private fun inputChanged() {
        val inputPassword = findViewById<EditText>(R.id.patient_register_page_password)
        inputPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                passwordValidation()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        val inputEmail = findViewById<EditText>(R.id.patient_register_page_email)
        inputEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                emailValidation()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
                var register_next = Intent(applicationContext, Patient_RegisterNext::class.java)
                startActivity(register_next)
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Wrong or repeat e-mail", Toast.LENGTH_SHORT,).show()
                updateUI(null)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    private fun passwordValidation() {
        val inputPassword = findViewById<EditText>(R.id.patient_register_page_password)
        val password: String = inputPassword?.text.toString().trim() {it <= ' '}
        val card1 = findViewById<CardView>(R.id.card1)
        val card2 = findViewById<CardView>(R.id.card2)
        val card3 = findViewById<CardView>(R.id.card3)

        if(password.length >= 6) {
            card1.setCardBackgroundColor(Color.GREEN)
            check1 = true
        } else {
            card1.setCardBackgroundColor(Color.RED)
            check1 = false
        }

        if(password.matches(".*\\d.*".toRegex())) {
            card2.setCardBackgroundColor(Color.GREEN)
            check2 = true
        } else {
            card2.setCardBackgroundColor(Color.RED)
            check2 = false
        }

        if(password.matches(".*[$&+,:;=?@#|'<>._^*()%!].*".toRegex())) {
            card3.setCardBackgroundColor(Color.GREEN)
            check3 = true
        } else {
            card3.setCardBackgroundColor(Color.RED)
            check3 = false
        }

        sumCheck()
    }

    private fun emailValidation() {
        val inputEmail = findViewById<EditText>(R.id.patient_register_page_email)
        val login: String = inputEmail?.text.toString().trim() {it <= ' '}

        check4 = login.matches(".*[@].*".toRegex()) && login.matches(".*[.].*".toRegex())
        sumCheck()
    }

    private fun sumCheck() {
        val button = findViewById<Button>(R.id.patient_register_page_button_next)
        button.isEnabled = check1 && check2 && check3 && check4 == true
    }

    private fun createUserData(nick: String) {
        var database = FirebaseDatabase.getInstance("https://account-dfca5-default-rtdb.europe-west1.firebasedatabase.app").reference.child("UserID").child(nick)
        var users = Users("", "", 0, "")
        database.setValue(users)
    }

    private fun nicknameValidation(login: String, password: String) {
        var database = FirebaseDatabase.getInstance("https://account-dfca5-default-rtdb.europe-west1.firebasedatabase.app").reference
        var getdata = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children) {
                    if(i.key == nickname) {
                        Toast.makeText(baseContext, "This nickname is already used", Toast.LENGTH_SHORT,).show()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT,).show()
            }
        }
        database.addValueEventListener(getdata)
        createAccount(login,password)
        createUserData(nickname)
    }
}