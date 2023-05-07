package com.solar.firebase.fcm.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class FcmRequestBody(
    @Json(name = "message")
    val message: Message = Message()
)