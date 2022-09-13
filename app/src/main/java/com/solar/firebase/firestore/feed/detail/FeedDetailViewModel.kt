package com.solar.firebase.firestore.feed.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solar.firebase.firestore.FireStoreHelper
import com.solar.firebase.firestore.FireStoreManager
import com.solar.firebase.firestore.FireStoreResult
import com.solar.firebase.firestore.FireStoreState
import com.solar.firebase.firestore.feed.Feed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class FeedDetailViewModel : ViewModel() {

    private val _fireStoreUiState: MutableStateFlow<FireStoreState<Feed>> =
        MutableStateFlow(FireStoreState.Loading)
    val fireStoreUiState: StateFlow<FireStoreState<Feed>> = _fireStoreUiState

    private val _feedDetailMutableStateFlow = MutableStateFlow<Feed?>(Feed())
    val feedDetailStateFlow = _feedDetailMutableStateFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Feed())

    fun loadFeedDetail(uid: String) {
        FireStoreHelper.readDocumentObject<Feed>("Feeds", uid) { state ->
            when (state) {
                is FireStoreState.Idle -> _fireStoreUiState.value = state
                is FireStoreState.Loading -> _fireStoreUiState.value = state
                is FireStoreState.Success -> {
                    println("Success ${state.data}")
                    _feedDetailMutableStateFlow.value = state.data
                    _fireStoreUiState.value = state
                }
                is FireStoreState.Error -> _fireStoreUiState.value = state
            }
        }
    }
}
