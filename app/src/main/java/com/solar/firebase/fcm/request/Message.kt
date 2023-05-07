package com.solar.firebase.fcm.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Message(
    @Json(name = "data")
    val `data`: Data = Data(),
    @Json(name = "notification")
    val notification: Notification = Notification(),
    @Json(name = "topic")
    val topic: String = ""
)