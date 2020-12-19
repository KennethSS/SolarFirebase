package com.sample.basefirebase.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.sample.basefirebase.FirebaseActivity
import com.sample.basefirebase.R

/**
 *  app/google-service.json
 *
 *  1. Google
 *  - implementation 'com.google.android.gms:play-services-auth:18.0.0'
 *  - implementation 'com.firebaseui:firebase-ui-auth:4.3.1'
 *  - apply plugin: 'com.google.gms.google-services'
 *
 */
abstract class AbstractFirebaseAuthActivity : FirebaseActivity(){

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val callbackManager: CallbackManager by lazy { CallbackManager.Factory.create() }

    protected abstract fun onSuccess(data: FirebaseAuthModel)
    protected abstract fun onFailed(code: Int, message: String)

    fun login(type: FirebaseAuthType, loginBtn: LoginButton? = null) {
        when(type) {
            FirebaseAuthType.GOOGLE -> googleLogin()
            FirebaseAuthType.FACEBOOK -> facebookLogin(loginBtn)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        dismissProgress()
        callbackManager.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        Log.d(TAG,"requestCode $requestCode")
        Log.d(TAG,"resultCode $resultCode")

        when(requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)

                    if (account != null) {
                        firebaseAuthWithGoogle(account)
                    }

                } catch (e: ApiException) {
                    onFailed(e.statusCode, e.message?:"ApiException")
                }
            }
        }
    }

    private fun facebookLogin(btn: LoginButton?) {
        btn?.setReadPermissions("email", "public_profile")
        btn?.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d(TAG, "facebook:onSuccess:$result")
                if (result != null) {
                    handleFacebookAccessToken(result.accessToken)
                }
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                onFailed(404, error.message?:"ApiException")
            }
        })
        btn?.performClick()
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
                        if (user != null) {
                            onSuccess(mapCurrentUserToFirebaseAuthModel(user, token.token))
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
    }

    private fun googleLogin() {
        showProgress()
        val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        signIn(googleSignInClient)
    }

    private fun signIn(googleSignInClient: GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun mapCurrentUserToFirebaseAuthModel(user: FirebaseUser, token: String?): FirebaseAuthModel =
            FirebaseAuthModel(user.uid, token, user.email, user.displayName, user.photoUrl.toString())


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser
                        if (currentUser != null) {
                            onSuccess(mapCurrentUserToFirebaseAuthModel(currentUser, acct.idToken))

                            Log.d(TAG, "uid = " + currentUser.uid)
                            Log.d(TAG, "email = " + currentUser.email)
                            Log.d(TAG, "display name = " + currentUser.displayName)
                            Log.d(TAG, "photo url = " + currentUser.photoUrl.toString())
                            Log.d(TAG, "providerId = " + currentUser.providerId)
                            Log.d(TAG, "phoneNumber = " + currentUser.phoneNumber)
                        } else {
                            // Todo auth failed
                        }
                    }
                }
    }

    companion object {
        private const val RC_SIGN_IN = 0
        private const val TAG = "FirebaseLoginActivity"
    }
}