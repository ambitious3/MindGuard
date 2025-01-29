package com.example.mindguard

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExerciseActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        recyclerView = findViewById(R.id.recyclerView)
        backButton = findViewById(R.id.button_back)

        exerciseAdapter = ExerciseAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = exerciseAdapter

        loadExercises()

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun loadExercises() {
        val exercises = listOf(
            MindfulnessExercise("Ćwiczenie 1", "Ćwiczenie polega na regulacji oddechu oraz ustabilizowaniu go. Twoim zadaniem jest brać głębokie wdechy i powoli je wypuszczać. Jeden cykl powinien trwac 5-10 sekund. Ćwiczenie trwa 5 minut, zrelaksuj się. Za wykonanie ćwiczenia otrzymasz punkty.", 300),
            MindfulnessExercise("Ćwiczenie 2", "Ćwiczenie polega na zamknięciu oczu i skupieniu się na otaczających dźwiękach przez 10 minut. Na początku pozwól sobie na pełne skupienie na dźwiękach, które docierają do Ciebie z otoczenia (szum wiatru, śpiew ptaków). Następnie przenieś uwagę na dźwięki, które są delikatniejsze (np. oddech, bicie serca). Z każdym oddechem, pozwól dźwiękom wypełnić Twój umysł, eliminując inne myśli. Celem jest stworzenie stanu pełnej obecności i uwolnienia od stresu poprzez świadome słuchanie.", 600)
        )

        exerciseAdapter.submitList(exercises)
    }
}
