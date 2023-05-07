package com.solar.firebase.firestore.notice

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.solar.firebase.firestore.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoticeDetailViewModel : ViewModel() {

    private val _stateFlow = MutableStateFlow<UiState<Any>>(UiState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    val title = MutableStateFlow("")
    val content = MutableStateFlow("")

    fun loadNoticeDetail(id: String) {
        Firebase.firestore.collection("Notice")
            .document(id)
            .get()
            .addOnSuccessListener { snapshot ->
                val notice = snapshot.toObject(Notice::class.java) ?: Notice()
                title.value = notice.title
                content.value = notice.content
                _stateFlow.value = UiState.Success(Any())
            }
            .addOnFailureListener { _stateFlow.value = UiState.Error(it) }
    }
}
