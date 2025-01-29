package com.example.mindguard

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mindguard.databinding.ItemExerciseBinding

class ExerciseAdapter : ListAdapter<MindfulnessExercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }

    class ExerciseViewHolder(private val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: MindfulnessExercise) {
            binding.exerciseName.text = exercise.name
            binding.exerciseDescription.text = exercise.description
            binding.exerciseDuration.text = "Czas trwania: ${exercise.duration / 60} min"

            binding.startExerciseButton.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, TimerActivity::class.java).apply {
                    putExtra("exercise_name", exercise.name)
                    putExtra("exercise_duration", exercise.duration)
                }
                context.startActivity(intent)

                Log.d("ExerciseAdapter", "Rozpocznij: ${exercise.name}")
            }
        }
    }

    class ExerciseDiffCallback : DiffUtil.ItemCallback<MindfulnessExercise>() {
        override fun areItemsTheSame(oldItem: MindfulnessExercise, newItem: MindfulnessExercise): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: MindfulnessExercise, newItem: MindfulnessExercise): Boolean {
            return oldItem == newItem
        }
    }
}
