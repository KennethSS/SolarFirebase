package com.solar.firebase

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> AppCompatActivity.observeViewModel(flow: Flow<T>, action: (T) -> Unit) {
    lifecycleScope.launchWhenStarted { flow.collect { action(it) } }
}
