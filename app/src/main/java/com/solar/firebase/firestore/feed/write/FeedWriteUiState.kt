package com.solar.firebase.firestore.feed.write

sealed interface FeedWriteUiState {
    object Idle : FeedWriteUiState
    object Success : FeedWriteUiState
}
