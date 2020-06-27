package com.sample.basefirebase.database

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*

object FirebaseStorageManager {
    fun upload(reference: String, uri: Uri, result: (uri: Uri?) -> Unit) {
        val storageReference = FirebaseStorage
                .getInstance()
                .getReference(reference)
                .child(UUID.randomUUID().toString())

        storageReference.putFile(uri)
                .continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation storageReference.downloadUrl
                })
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        result(task.result)
                    } else {
                        result(null)
                    }
                }
    }
}