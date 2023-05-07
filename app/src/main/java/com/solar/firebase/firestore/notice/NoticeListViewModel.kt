package com.solar.firebase.firestore.notice

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.solar.firebase.firestore.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoticeListViewModel : ViewModel() {

    private val _stateFlow = MutableStateFlow<UiState<List<Notice>>>(UiState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        loadNoticeList()
    }

    private fun loadNoticeList() {
        noticeStore()
            .addSnapshotListener { value, error ->
                val noticeList = value?.documents?.map { snapshot ->
                    (snapshot.toObject(Notice::class.java) ?: Notice()).copy(id = snapshot.id)
                }.orEmpty()
                _stateFlow.value = UiState.Success(noticeList)
            }
    }

    fun deleteNotice(id: String) {
        Firebase.firestore.collection("Notice")
            .document(id)
            .delete()
            .addOnSuccessListener {
                val state = _stateFlow.value
                if (state is UiState.Success<List<Notice>>) {
                    _stateFlow.value = UiState.Success(state.data.filter { it.id != id })
                }
            }
    }

    private fun noticeStore() = Firebase.firestore.collection("Notice")
}
