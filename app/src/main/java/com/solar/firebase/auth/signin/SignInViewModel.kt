package com.solar.firebase.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    private val _signInUiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState.Idle)
    val signInUiState: StateFlow<SignInUiState> = _signInUiState

    fun signIn() {
        if (email.value.isNullOrEmpty() && pw.value.isNullOrEmpty()) {
        } else {
            _signInUiState.value = SignInUiState.Loading
            Firebase.auth.signInWithEmailAndPassword(EMAIL, PW)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        getUserLogging()
                        val user = Firebase.auth.currentUser
                        _signInUiState.value = SignInUiState.SuccessSignIn(user)
                    } else {
                        println("Exception: ${task.exception}")
                        _signInUiState.value =
                            SignInUiState.ErrorSignIn("Failed Sign In: ${task.exception}")
                    }
                }
        }
    }

    fun signOut() { }

    private fun getUserLogging() {
        val user = Firebase.auth.currentUser
        println("User UID: ${user?.uid}")
        println("User Name: ${user?.displayName}")
        println("User Email: ${user?.email}")
        println("User PhoneNumber: ${user?.phoneNumber}")
        println("User isAnonymous: ${user?.isAnonymous}")
    }

    companion object {
        const val EMAIL = "wlsrhkd4024@naver.com"
        const val PW = "rhrnak40"
    }
}
