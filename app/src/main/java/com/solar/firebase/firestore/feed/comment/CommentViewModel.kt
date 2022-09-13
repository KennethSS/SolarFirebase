package com.solar.firebase.firestore.feed.comment

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommentViewModel : ViewModel() {

    val inputComment = MutableStateFlow("")

    private val _commentsStateFlow = MutableStateFlow<CommentState>(CommentState.Loading)
    val commentsStateFlow = _commentsStateFlow.asStateFlow()

    var feedId: String = ""

    fun addComment() {
        Firebase.firestore.collection("Feeds")
            .document(feedId)
            .collection("Comments")
            .add(
                Comment(
                    comment = inputComment.value,
                    nickname = "nickname"
                )
            )
    }

    fun loadComment(id: String) {
        Firebase.firestore.collection("Feeds")
            .document(id)
            .collection("Comments")
            .addSnapshotListener { value, error ->
                val comments =
                    value?.documents?.map { it.toObject<Comment>() ?: Comment() }.orEmpty()
                _commentsStateFlow.value = CommentState.Success(comments)
            }
    }
}
