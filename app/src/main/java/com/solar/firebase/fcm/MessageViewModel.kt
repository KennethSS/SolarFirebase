package com.solar.firebase.fcm

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.auth.oauth2.GoogleCredentials
import com.solar.firebase.fcm.request.Data
import com.solar.firebase.fcm.request.FcmRequestBody
import com.solar.firebase.fcm.request.Message
import com.solar.firebase.fcm.request.Notification
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.util.*

class MessageViewModel : ViewModel() {

    fun sendMessage(
        am: AssetManager,
        message: String
    ) = viewModelScope.launch {
        val acessToken = getAccessToken(am)
        println("acessToken $acessToken")
        /*val accessToken = RetrofitModule.googleAuthService().getToken().accessToken
        val bearer = "Bearer $accessToken"
        val body = getNotificationBody()
        RetrofitModule.fcmService().sendFcm(bearer, body)*/
    }

    private fun getNotificationBody(): FcmRequestBody {
        return FcmRequestBody(
            message = Message(
                topic = "topic",
                notification = Notification(
                    title = "title",
                    body = "body"
                ),
                data = Data(
                    storyId = "storyId"
                )
            )
        )
    }

    private fun getAccessToken(am: AssetManager): String? {
        return try {
            val file = am.open("ex-coding-161a625816b6.json")
            val googleCredentials: GoogleCredentials = GoogleCredentials
                .fromStream(file)
                .createScoped()
            return googleCredentials.accessToken.tokenValue
        } catch (e: Exception) {
            e.stackTrace.forEach {
                println("Stack ${it}")
            }
            println("Exception ${e.message}")
            println("Exception ${e.cause?.message}")
            println("Exception ${e.cause?.cause}")
            "token"
        }
    }
}
