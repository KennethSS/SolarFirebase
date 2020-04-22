package com.solar.firebase.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val _progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onResume() {
        super.onResume()

    }

    fun showProgress() {
        _progressDialog.show()
    }

    fun dismissProgress() {
        if (_progressDialog.isShowing)
            _progressDialog.dismiss()
    }
}