package com.solar.firebase.extension

import android.app.Activity
import com.solar.solarktx.toast

fun Activity.toastAndFinish(msg: String) {
    toast(msg)
    finish()
}