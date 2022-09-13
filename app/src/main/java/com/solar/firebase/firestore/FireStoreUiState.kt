package com.solar.firebase.firestore

sealed class FireStoreUiState {
    object SuccessWrite : FireStoreUiState()
    data class SuccessRead<out T>(val data: T) : FireStoreUiState()
    data class Error(val msg: String) : FireStoreUiState()
    object Loading : FireStoreUiState()
    object Idle : FireStoreUiState()
}
