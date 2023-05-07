package com.solar.firebase.fcm.request

data class GetTokenRequestBody(
    //val code: String = "4%2F0ARtbsJrKbR7Ay-7__Hmem3Cr37HECVq8TdWLu1HiL7_x4XIBoPIZKVKnGxs_wW3WzUrI0A",
    val redirect_uri: String = "https://localhost:8080/auth/google/callback&grant_type=authorization_code",
    val client_id: String = "881575850643-7jiehsssgtvfo885kc9vmmm7g8o2bt4q.apps.googleusercontent.com",
    val client_secret: String = "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",

    val scope: String = "",
    val grant_type: String = "authorization_code"
)