package com.solar.firebase

import android.app.Application
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging
import java.io.FileInputStream
import java.util.*

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    val options = FirebaseOptions.Builder()

    FirebaseApp.initializeApp(this)
    FirebaseMessaging.getInstance().isAutoInitEnabled = true
    FirebaseMessaging.getInstance().subscribeToTopic("ABC123")

    //주제 취소
    //FirebaseMessaging.getInstance().unsubscribeFromTopic("주제1");

    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
  }


}