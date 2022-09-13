package com.solar.firebase.firestore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*

abstract class FireStoreViewModel<T> : ViewModel() {

    private val _fireStoreState: MutableSharedFlow<FireStoreState<T>> =
        MutableSharedFlow(replay = 1)
    val fireStoreState = _fireStoreState.asSharedFlow()

    protected fun setState(action: () -> FireStoreState<T>) {
        viewModelScope.launch { _fireStoreState.emit(action()) }
    }

    private fun sample2(): HashMap<String, Any> {
        return hashMapOf(
            "array" to List(10) { sample() }
        )
    }

    private fun sample(): HashMap<String, Any?> {
        return hashMapOf(
            "stringExample" to "Hello world!",
            "booleanExample" to true,
            "numberExample" to 3.14159265,
            "dateExample" to Timestamp(Date()),
            "listExample" to arrayListOf(1, 2, 3),
            "nullExample" to null
        )
    }
}
