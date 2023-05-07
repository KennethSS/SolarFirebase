package com.solar.firebase.fcm.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "story_id")
    val storyId: String = ""
)