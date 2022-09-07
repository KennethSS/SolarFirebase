package com.solar.firebase

import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.solar.firebase.firestore.FireStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

class FireStoreViewModel : ViewModel() {

    private val _fireStoreUiState: MutableStateFlow<FireStoreUiState> = MutableStateFlow(FireStoreUiState.Idle)
    val fireStoreUiState: StateFlow<FireStoreUiState> = _fireStoreUiState

    fun write(collection: String, document: String, data: Any) {
        FireStoreManager.setDocument(
            "collection 1", "document 1", sample2(),
            onSuccess = { _fireStoreUiState.value = FireStoreUiState.SuccessWrite },
            onError = { throwable -> _fireStoreUiState.value = FireStoreUiState.Error(throwable.message.orEmpty()) }
        )
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
