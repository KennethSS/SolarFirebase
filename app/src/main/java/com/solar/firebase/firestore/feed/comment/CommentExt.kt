package com.solar.firebase.firestore.feed.comment

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("timestamp")
fun setTimeStamp(tv: TextView, timestamp: Timestamp) {
    val format = SimpleDateFormat("yyyy MM dd HH:mm:ss", Locale.KOREA)
    tv.text = format.format(timestamp.toDate())
}