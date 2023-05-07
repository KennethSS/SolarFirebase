package com.solar.firebase.firestore.feed.write

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.solar.firebase.firestore.FireStoreState
import com.solar.firebase.firestore.FireStoreViewModel
import com.solar.firebase.firestore.feed.Feed
import java.util.*

class FeedWriteViewModel : FireStoreViewModel<Any>() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    var uri: Uri? = null

    private fun uploadImage(next: (uri: Uri?) -> Unit) {
        if (uri != null) {
            val ref = FirebaseStorage
                .getInstance()
                .getReference("Feeds")
                .child(UUID.randomUUID().toString())

            ref.putFile(uri!!)
                .continueWithTask(
                    Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        return@Continuation ref.downloadUrl
                    }
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        next(task.result)
                    } else {
                        next(null)
                    }
                }
        } else {
            next(null)
        }
    }

    fun register() {
        if (title.value.isNullOrEmpty()) {
            setState { FireStoreState.Error("제목을 입력해주세요") }
            return
        }
        if (content.value.isNullOrEmpty()) {
            setState { FireStoreState.Error("내용을 입력해주세요") }
            return
        }

        setState { FireStoreState.Loading }

        uploadImage { uri ->
            Firebase.firestore.collection("Feeds")
                .add(
                    Feed(
                        Firebase.auth.currentUser?.uid.orEmpty(),
                        title.value.orEmpty(),
                        content.value.orEmpty()
                    )
                )
                .addOnSuccessListener { setState { FireStoreState.Success(Any()) } }
                .addOnFailureListener { setState { FireStoreState.Error("업로드 중 오류가 발생했습니다.") } }
        }
    }
}
