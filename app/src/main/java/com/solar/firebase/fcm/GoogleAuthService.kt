package com.solar.firebase.fcm

import com.solar.firebase.fcm.request.FcmRequestBody
import com.solar.firebase.fcm.request.GetTokenRequestBody
import com.solar.firebase.fcm.request.Message
import com.solar.firebase.fcm.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleAuthService {

    @POST("/o/oauth2/v2/auth")
    suspend fun getToken(
        @Body body: GetTokenRequestBody = GetTokenRequestBody()
    ): TokenResponse

//code=4%2F0ARtbsJrKbR7Ay-7__Hmem3Cr37HECVq8TdWLu1HiL7_x4XIBoPIZKVKnGxs_wW3WzUrI0A
// &redirect_uri=https%3A%2F%2Fdevelopers.google.com%2Foauthplayground
// &client_id=881575850643-7jiehsssgtvfo885kc9vmmm7g8o2bt4q.apps.googleusercontent.com
// &client_secret=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
// &scope=
// &grant_type=authorization_code
}

//
