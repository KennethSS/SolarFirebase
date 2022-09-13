package com.solar.firebase.firestore

sealed class FireStoreState<out T> {
    object Idle : FireStoreState<Nothing>()
    object Loading : FireStoreState<Nothing>()
    data class Success<T>(val data: T) : FireStoreState<T>()
    data class Error(val msg: String) : FireStoreState<Nothing>()
}
