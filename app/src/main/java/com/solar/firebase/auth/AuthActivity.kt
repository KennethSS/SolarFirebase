package com.solar.firebase.auth

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sample.basefirebase.auth.AbstractFirebaseAuthActivity
import com.sample.basefirebase.auth.FirebaseAuthModel
import com.sample.basefirebase.auth.FirebaseAuthType
import com.solar.firebase.auth.signin.SignInActivity
import com.solar.firebase.databinding.ActivityAuthBinding
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_login.facebook_login

/**
 *  Created by Kenneth on 12/18/20
 */
class AuthActivity : AbstractFirebaseAuthActivity() {

    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnLoginFacebook.setOnClickListener { login(FirebaseAuthType.FACEBOOK, facebook_login) }
        binding.btnLoginGoogle.setOnClickListener { login(FirebaseAuthType.GOOGLE) }
        binding.email.setOnClickListener { startActivity(Intent(this, SignInActivity::class.java)) }
    }

    override fun onSuccess(data: FirebaseAuthModel) {
        Glide.with(this)
                .load(data.photoUrl)
                .into(binding.profileImg)
        binding.nickname.text = data.nickname
        binding.email.text = data.email
        binding.uid.text = data.uid
    }

    override fun onFailed(code: Int, message: String) {

    }
}