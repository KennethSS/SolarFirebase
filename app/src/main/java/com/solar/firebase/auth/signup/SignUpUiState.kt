package com.solar.firebase.auth.signup

import com.google.firebase.auth.FirebaseUser

sealed interface SignUpUiState {
    data class Success(val user: FirebaseUser?) : SignUpUiState
    data class Error(val msg: String) : SignUpUiState
    object Loading : SignUpUiState
    object Idle : SignUpUiState
}
