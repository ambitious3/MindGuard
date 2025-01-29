package com.example.mindguard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.mindguard.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (isValidEmail(email) && isValidPassword(password)) {
                    registerUser(email, password)
                } else {
                    Toast.makeText(this, "Niepoprawny e-mail lub hasło", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Wypełnij wszystkie luki", Toast.LENGTH_SHORT).show()
            }
        }

        val backButton = findViewById<Button>(R.id.button_back_timer)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Rejestracja udana!", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Log.e("Register", "Rejestracja nieudana: ${task.exception?.message}")
                    Toast.makeText(this, "Błąd rejestracji: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
