package com.solar.firebase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.sample.basefirebase.FirebaseLoginActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : FirebaseLoginActivity() {
    private val TAG = "LoginActivity"
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val facebookCallbackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login_facebook.setOnClickListener { facebook_login.performClick() }
        btn_login_google.setOnClickListener { googleLogin() }
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

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
    }
}