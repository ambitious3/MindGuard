package com.example.mindguard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindguard.ui.theme.MindGuardTheme
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate

class MainActivity : ComponentActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var firebaseHelper: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseHelper = FirebaseHelper(this)

        setContent {
            MindGuardTheme {
                var points by remember { mutableStateOf(0L) }
                firebaseHelper.fetchPoints { fetchedPoints ->
                    points = fetchedPoints
                }

                MainScreen(
                    points = points,
                    onDailyLoginReward = { updateDailyPointsReward { updatedPoints -> points = updatedPoints } },
                    onMoodTrackerClick = {
                        startActivity(Intent(this, MoodActivity::class.java))
                    },
                    onMindfulnessClick = {
                        startActivity(Intent(this, ExerciseActivity::class.java))
                    },
                    onCommunityClick = {
                        startActivity(Intent(this, SupportCommunityActivity::class.java))
                    },
                    onEmergencyClick = {
                        startActivity(Intent(this, EmergencyActivity::class.java))
                    },
                    onSurveyClick = {
                        startActivity(Intent(this, SurveyActivity::class.java))
                    },
                    onLogoutClick = {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }

    private fun updateDailyPointsReward(onPointsUpdated: (Long) -> Unit) {
        firebaseHelper.updatePointsForLogin { updatedPoints ->
            Toast.makeText(this, "Otrzymałeś 50 punktów za codzienne logowanie!", Toast.LENGTH_SHORT).show()
            onPointsUpdated(updatedPoints)
        }
    }
}

@Composable
fun MainScreen(
    points: Long,
    onDailyLoginReward: () -> Unit,
    onMoodTrackerClick: () -> Unit,
    onMindfulnessClick: () -> Unit,
    onCommunityClick: () -> Unit,
    onEmergencyClick: () -> Unit,
    onSurveyClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        onDailyLoginReward()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Twoje punkty: $points", modifier = Modifier.padding(bottom = 16.dp))

            Button(onClick = onMoodTrackerClick) {
                Text(text = "Dodaj nastrój")
            }
            Button(onClick = onMindfulnessClick) {
                Text(text = "Ćwiczenia mindfulness")
            }
            Button(onClick = onSurveyClick) {
                Text(text = "Ankieta")
            }
            Button(onClick = onCommunityClick) {
                Text(text = "Społeczność wsparcia")
            }
            Button(onClick = onEmergencyClick) {
                Text(text = "Numery alarmowe")
            }
            Button(onClick = onLogoutClick) {
                Text(text = "Wyloguj się")
            }
        }
    }
}