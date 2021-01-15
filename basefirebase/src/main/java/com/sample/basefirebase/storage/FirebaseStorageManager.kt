package com.sample.basefirebase.storage

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.util.*

object FirebaseStorageManager {
    /**
     *  @param reference 'root/A/B'
     *  @param uri file Uri
     *  @param callback Firebase download Uri
     *  @param progress progress 0.0 ~ 100.0
     */
    fun upload(reference: String,
               uri: Uri,
               result: (downloadUri: Uri?) -> Unit,
               progress: (progress: Float) -> Unit) {

        val storageReference = FirebaseStorage
                .getInstance()
                .getReference(reference)
                .child(UUID.randomUUID().toString())

        storageReference.putFile(uri)
                .addOnProgressListener{
                    progress(
                            (it.bytesTransferred.toFloat() / it.totalByteCount.toFloat()) * 100
                    )
                }
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

    /**
     *
     */
    fun uploadFromMemory(reference: String, iv: ImageView) {
        val storageReference = FirebaseStorage
                .getInstance()
                .getReference(reference)
                .child(UUID.randomUUID().toString())

        iv.isDrawingCacheEnabled = true
        iv.buildDrawingCache()
        val bitmap = (iv.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        storageReference.putBytes(data)
                .addOnFailureListener {
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                }
                .addOnProgressListener(::progress)
    }

    private fun progress(task: UploadTask.TaskSnapshot) {
        Log.d(TAG, "TotalByteCount:${task.totalByteCount}")
        Log.d(TAG, "BytesTransferred:${task.bytesTransferred}")
    }

    private const val TAG = "FirebaseStorageManager"
}