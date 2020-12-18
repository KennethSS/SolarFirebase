package com.sample.basefirebase.storage.image

import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage

fun ImageView.setStorageImage(path: String) {
    GlideApp.with(this)
            .load(FirebaseStorage.getInstance().getReference(path))
            .into(this)
}