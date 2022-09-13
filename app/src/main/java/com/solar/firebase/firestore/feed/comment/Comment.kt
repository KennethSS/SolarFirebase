package com.solar.firebase.firestore.feed.comment

import com.google.firebase.Timestamp
import java.util.*

data class Comment(
    var comment: String = "",
    var nickname: String = "",
    val timestam: Timestamp = Timestamp(Date())
)
