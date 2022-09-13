package com.solar.firebase.firestore

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisibleLoading")
fun isVisibleIfLoading(view: View, state: FireStoreState<*>) {
    view.isVisible = state is FireStoreState.Loading
}