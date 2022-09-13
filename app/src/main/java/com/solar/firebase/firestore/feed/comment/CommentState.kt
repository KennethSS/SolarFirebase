package com.solar.firebase.firestore.feed.comment

sealed interface CommentState {
    object Loading : CommentState
    data class Success(val comments: List<Comment>) : CommentState
}
