package com.sample.basefirebase.firestore

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

object FireStoreHelper {

    private const val TAG = "FireStoreHelper"

    fun writeData(collection: String, document: String, data: HashMap<String, Any>) {
        FirebaseFirestore.getInstance()
                .collection(collection)
                .document(document)
                .set(data) // SetOptions.merge()
                .addOnFailureListener {

                }
                .addOnFailureListener { exception ->

                }
    }

    fun readAllDocument(collection: String) {
        FirebaseFirestore.getInstance()
                .collection(collection)
                .get()
                .addOnSuccessListener { result ->
                    result.forEach { document ->
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }.addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
    }

    fun <T> readDocumentData(collection: String,
                             document: String,
                             cls: Class<T>,
                             result: (result: T) -> Unit) {
        FirebaseFirestore.getInstance()
                .collection(collection)
                .document(document)
                .get()
                .addOnSuccessListener { documentSnapshot: DocumentSnapshot? ->
                    if (documentSnapshot != null) {
                        val data = documentSnapshot.toObject(cls)
                        data?.let(result)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
    }
}