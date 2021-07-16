package com.solar.firebase.crashlytics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.solar.firebase.R
import kotlinx.android.synthetic.main.activity_crashlytics.*
import java.lang.RuntimeException

class CrashlyticsActivity : AppCompatActivity(R.layout.activity_crashlytics) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    test_crash.setOnClickListener {
      throw RuntimeException("Test Crash")
    }
  }
}