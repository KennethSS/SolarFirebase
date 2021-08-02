package com.solar.firebase.firestore

sealed class FireStoreResult<out T> {
  data class Success<T>(val data: T) : FireStoreResult<T>()
  data class Error(val throwable: Throwable) : FireStoreResult<Nothing>()
}
