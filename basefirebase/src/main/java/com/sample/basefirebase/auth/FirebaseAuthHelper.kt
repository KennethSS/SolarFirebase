package com.sample.basefirebase.auth

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 *  Created by Kenneth on 12/18/20
 */
object FirebaseAuthHelper {
    fun logout() {
        Firebase.auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }
}