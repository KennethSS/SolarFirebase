package com.solar.firebase.firestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

object FireStoreHelper {
    inline fun <reified T> readDocumentObject(
        collection: String,
        document: String,
        crossinline result: (FireStoreState<T>) -> Unit
    ) {
        Firebase.firestore.collection(collection).document(document).get()
            .addOnSuccessListener { documentSnapshot ->
                documentSnapshot.toObject<T>()?.let {
                    result(FireStoreState.Success(it))
                }
            }.addOnFailureListener { exception ->
                result(FireStoreState.Error(exception.message.orEmpty()))
            }
    }
}