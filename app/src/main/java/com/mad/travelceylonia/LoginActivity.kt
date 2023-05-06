package com.mad.travelceylonia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Initialize Firebase Auth
        auth = Firebase.auth

        val registertext: TextView = findViewById(R.id.noAccount)

        registertext.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.LoginButton)

        loginButton.setOnClickListener {
            performLogin()
        }


    }

    private fun performLogin() {

        val email: EditText = findViewById(R.id.login_username)
        val password: EditText = findViewById(R.id.login_password)

        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to the main activity

                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext, "Success.",
                        Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Authentication failed.${it.localizedMessage}", Toast.LENGTH_SHORT,)
                    .show()
            }
    }


}