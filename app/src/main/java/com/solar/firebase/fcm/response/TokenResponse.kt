package com.solar.firebase.fcm.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class TokenResponse(
    @Json(name = "access_token")
    val accessToken: String = "",
    @Json(name = "expires_in")
    val expiresIn: Int = 0,
    @Json(name = "scope")
    val scope: String = "",
    @Json(name = "token_type")
    val tokenType: String = ""
)