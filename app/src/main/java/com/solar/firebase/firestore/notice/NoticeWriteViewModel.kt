package com.solar.firebase.firestore.notice

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.solar.firebase.firestore.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoticeWriteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Any>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    val title = MutableStateFlow("")
    val content = MutableStateFlow("")
    var id: String = ""

    fun register() {
        Firebase.firestore.collection("Notice")
            .add(
                Notice(
                    title = title.value,
                    content = title.value
                )
            )
            .addOnSuccessListener {
                _uiState.value = UiState.Success(Any())
            }
    }

    fun loadNoticeDetail() {
        if (id.isNotEmpty()) {
            Firebase.firestore.collection("Notice")
                .document(id)
                .get()
                .addOnSuccessListener { snapshot ->
                    val notice = snapshot.toObject(Notice::class.java) ?: Notice()
                    title.value = notice.title
                    content.value = notice.content
                }
                .addOnFailureListener { }
        }
    }
}
