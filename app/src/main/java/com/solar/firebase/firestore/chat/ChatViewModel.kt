package com.solar.firebase.firestore.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class ChatViewModel : ViewModel() {

    val input = MutableStateFlow("")

    fun send() {
        val text = input.value

        Firebase.firestore.collection("Chats")
            .document("ff")
            .collection("")
            .addSnapshotListener { value, error ->

            }
    }
}