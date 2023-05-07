package com.solar.firebase.firestore

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class AbstractViewModel : ViewModel() {
    private val _stateFlow = MutableStateFlow(UiState.Loading)
    val stateFlow = _stateFlow.asStateFlow()
}