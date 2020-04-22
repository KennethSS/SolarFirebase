package com.solar.firebase.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics

abstract class FirebaseAnalyticsActivity : AppCompatActivity() {
    lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        //firebaseAnalytics.setUserId()
        //firebaseAnalytics.setUserProperty()
        //firebaseAnalytics.setSessionTimeoutDuration(1000 * 60 * 30)
        firebaseAnalytics.logEvent("TRACK_CODE", Bundle().apply {
            putString(FirebaseAnalytics.Event.SHARE, "id")
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, "detail")
            putString(FirebaseAnalytics.UserProperty.SIGN_UP_METHOD, "sign up")
        })
    }

    override fun onResume() {
        super.onResume()
        firebaseAnalytics.setCurrentScreen(this, SCREEN_NAME, null /* class override */)
    }

    abstract val SCREEN_NAME: String
    abstract val SCREEN_NAME_EN: String
}