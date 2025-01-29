package com.example.mindguard

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MoodActivity : AppCompatActivity() {

    private lateinit var moodRadioGroup: RadioGroup
    private lateinit var moodNote: EditText
    private lateinit var saveMoodButton: Button
    private lateinit var backButton: Button
    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)

        moodRadioGroup = findViewById(R.id.moodRadioGroup)
        moodNote = findViewById(R.id.moodNote)
        saveMoodButton = findViewById(R.id.saveMoodButton)
        backButton = findViewById(R.id.button_back)

        saveMoodButton.setOnClickListener {
            saveMood()
        }
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun saveMood() {
        val selectedMoodId = moodRadioGroup.checkedRadioButtonId
        val moodText = findViewById<RadioButton>(selectedMoodId)?.text.toString()
        val noteText = moodNote.text.toString()
        val timestamp = Calendar.getInstance().time

        if (moodText.isNotEmpty()) {
            val userId = firebaseAuth.currentUser?.uid

            if (userId == null) {
                Toast.makeText(this, "Nie zalogowano!", Toast.LENGTH_SHORT).show()
                return
            }

            val moodData = hashMapOf(
                "userId" to userId,
                "mood" to moodText,
                "note" to noteText,
                "timestamp" to timestamp
            )

            firestore.collection("moods")
                .add(moodData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Nastrój zapisany!", Toast.LENGTH_SHORT).show()
                    updatePoints(userId)
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Błąd zapisu: ${e.message}", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "Proszę wybrać nastrój", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePoints(userId: String) {
        val userDocRef = firestore.collection("users").document(userId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(userDocRef)
            val currentPoints = snapshot.getLong("points") ?: 0
            transaction.update(userDocRef, "points", currentPoints + 10)
        }.addOnSuccessListener {
            Toast.makeText(this, "Punkty zaktualizowane!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Błąd aktualizacji punktów: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
