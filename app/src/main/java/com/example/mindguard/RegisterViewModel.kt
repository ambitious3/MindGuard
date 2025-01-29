package com.example.mindguard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun onRegisterClicked() {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()

        if (emailValue.isNotEmpty() && passwordValue.isNotEmpty()) {
            registerUser(emailValue, passwordValue)
        } else {
        }
    }

    private fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                        } else {
                        }
                    }
            } catch (e: Exception) {
            }
        }
    }
}
