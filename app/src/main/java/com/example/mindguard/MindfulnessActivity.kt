package com.example.mindguard
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mindguard.R

class MindfulnessActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mindfulnessAdapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mindfulness)

        recyclerView = findViewById(R.id.recyclerView)
        mindfulnessAdapter = ExerciseAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mindfulnessAdapter

        loadMindfulnessExercises()
    }

    private fun loadMindfulnessExercises() {
        val exercises = listOf(
            MindfulnessExercise("Ćwiczenie 1", "Opis ćwiczenia 1", 300),
            MindfulnessExercise("Ćwiczenie 2", "Opis ćwiczenia 2", 600)
        )

        mindfulnessAdapter.submitList(exercises)
    }
}
