package com.sample.basefirebase.auth

/**
 *  Created by Kenneth on 12/18/20
 */
data class FirebaseAuthModel(
        val uid: String,
        val email: String?,
        val nickname: String?,
        val photoUrl: String?
)