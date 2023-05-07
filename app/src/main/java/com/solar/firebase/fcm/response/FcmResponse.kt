package com.solar.firebase.fcm.response

import com.squareup.moshi.Json

data class FcmResponse(
    @Json(name = "name")
    val name: String
)