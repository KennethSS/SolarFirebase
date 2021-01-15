package com.solar.firebase.auth

import android.os.Bundle
import com.bumptech.glide.Glide
import com.sample.basefirebase.auth.AbstractFirebaseAuthActivity
import com.sample.basefirebase.auth.FirebaseAuthModel
import com.sample.basefirebase.auth.FirebaseAuthType
import com.solar.firebase.R
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_login.btn_login_facebook
import kotlinx.android.synthetic.main.activity_login.btn_login_google
import kotlinx.android.synthetic.main.activity_login.facebook_login

/**
 *  Created by Kenneth on 12/18/20
 */
class AuthActivity : AbstractFirebaseAuthActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        btn_login_facebook.setOnClickListener { login(FirebaseAuthType.FACEBOOK, facebook_login) }
        btn_login_google.setOnClickListener { login(FirebaseAuthType.GOOGLE) }
    }

    override fun onSuccess(data: FirebaseAuthModel) {
        Glide.with(this)
                .load(data.photoUrl)
                .into(profile_img)
        nickname.text = data.nickname
        email.text = data.email
        uid.text = data.uid
    }

    override fun onFailed(code: Int, message: String) {

    }
}