package com.solar.firebase.auth.signin

import com.google.firebase.auth.FirebaseUser

sealed class SignInUiState {
    data class SuccessSignIn(val user: FirebaseUser?) : SignInUiState()
    data class ErrorSignIn(val msg: String) : SignInUiState()
    object Loading : SignInUiState()
    object Idle : SignInUiState()
}
