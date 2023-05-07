package com.solar.firebase.firestore.chat

import com.google.firebase.Timestamp

data class Chat(
    val thumbnail: String,
    val message: String,
    val timestamp: Timestamp
)
