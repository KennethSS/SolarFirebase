package com.solar.firebase.presentation

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging

class BaseApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    FirebaseApp.initializeApp(this)
    FirebaseMessaging.getInstance().isAutoInitEnabled = true
    FirebaseMessaging.getInstance().subscribeToTopic("ABC123")

    //주제 취소
    //FirebaseMessaging.getInstance().unsubscribeFromTopic("주제1");

    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
  }
}