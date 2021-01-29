package com.solar.firebase.presentation

import android.os.Bundle
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.sample.basefirebase.auth.AbstractFirebaseAuthActivity
import com.sample.basefirebase.auth.FirebaseAuthModel
import com.sample.basefirebase.auth.FirebaseAuthType
import com.solar.firebase.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AbstractFirebaseAuthActivity() {
    private val TAG = "LoginActivity"
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val facebookCallbackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onSuccess(data: FirebaseAuthModel) { }

    override fun onFailed(code: Int, message: String) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login_facebook.setOnClickListener { facebook_login.performClick() }
        btn_login_google.setOnClickListener { login(FirebaseAuthType.GOOGLE) }

        //startAuthLogin()

        // Initialize Facebook Login button

        /*facebook_login.setReadPermissions("email", "public_profile")
        facebook_login.registerCallback(facebookCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val gson = Gson()
                val json = gson.toJson(loginResult)

                val facebookToken = loginResult.accessToken
                handleFacebookAccessToken(facebookToken)

                with(facebookToken) {
                    Log.d(TAG, applicationId)
                    Log.d(TAG, userId)
                    Log.d(TAG, gson.toJson(facebookToken.permissions))
                }

                Log.d(TAG, "facebook:onSuccess:$json")
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                // ...
            }
        })// ...*/

    }


}