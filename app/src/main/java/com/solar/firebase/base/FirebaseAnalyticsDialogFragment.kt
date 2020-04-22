package com.solar.firebase.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.firebase.analytics.FirebaseAnalytics

abstract class FirebaseAnalyticsDialogFragment : AppCompatDialogFragment() {
    lateinit var firebaseAnalytics: FirebaseAnalytics

    abstract val SCREEN_NAME: String
    abstract val SCREEN_NAME_EN: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(view.context)
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        activity?.run {
            firebaseAnalytics.setCurrentScreen(this, SCREEN_NAME, null /* class override */)
        }
    }
}