package com.solar.firebase.firestore.feed.write

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.solar.firebase.firestore.FireStoreState
import com.solar.firebase.firestore.FireStoreViewModel
import com.solar.firebase.firestore.feed.Feed

class FeedWriteViewModel : FireStoreViewModel<Any>() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    fun register() {
        println("Title: ${title.value}")

        if (title.value.isNullOrEmpty()) {
            println("Title1: ${title.value}")
            setState { FireStoreState.Error("제목을 입력해주세요") }
            return
        }

        println("content: ${content.value}")
        if (content.value.isNullOrEmpty()) {
            println("content 1: ${content.value}")
            setState { FireStoreState.Error("내용을 입력해주세요") }
            return
        }

        setState { FireStoreState.Loading }

        Firebase.firestore.collection("Feeds")
            .add(Feed(Firebase.auth.currentUser?.uid.orEmpty(), title.value.orEmpty(), content.value.orEmpty()))
            .addOnSuccessListener { setState { FireStoreState.Success(Any()) } }
            .addOnFailureListener { setState { FireStoreState.Error("업로드 중 오류가 발생했습니다.") } }
    }
}
