package com.solar.firebase.fcm.request

import com.squareup.moshi.Json

data class Notification(
    @Json(name = "body")
    val body: String = "",
    @Json(name = "title")
    val title: String = ""
)
