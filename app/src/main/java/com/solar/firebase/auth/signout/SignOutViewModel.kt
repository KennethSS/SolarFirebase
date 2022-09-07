package com.solar.firebase.auth.signout

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignOutViewModel : ViewModel() {

    fun signOut() {
        Firebase.auth.signOut()
    }
}
