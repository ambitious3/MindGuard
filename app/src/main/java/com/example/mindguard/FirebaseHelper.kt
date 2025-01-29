package com.example.mindguard

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class FirebaseHelper(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun addPoints(pointsToAdd: Int) {
        val editor = sharedPreferences.edit()
        val currentPoints = sharedPreferences.getLong("points", 0L)
        editor.putLong("points", currentPoints + pointsToAdd)
        editor.apply()
    }

    fun fetchPoints(onSuccess: (Long) -> Unit) {
        val points = sharedPreferences.getLong("points", 0L)
        onSuccess(points)
    }

    fun updatePointsForLogin(onPointsUpdated: (Long) -> Unit) {
        val lastLoginDate = sharedPreferences.getString("lastLoginDate", "")
        val currentDate = Calendar.getInstance().time.toString()

        if (lastLoginDate != currentDate) {
            addPoints(50)
            val editor = sharedPreferences.edit()
            editor.putString("lastLoginDate", currentDate)
            editor.apply()
            fetchPoints(onPointsUpdated)
        } else {
            fetchPoints(onPointsUpdated)
        }
    }
}
