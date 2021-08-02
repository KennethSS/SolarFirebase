package com.solar.firebase.firestore

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

object FireStoreManager {



  fun add(collection: String,
          data: Any,
  ) {

    Firebase.firestore.collection(collection)
      .add(data)
      .addOnSuccessListener { documentReference ->
        documentReference.id
      }
      .addOnFailureListener { exception ->

      }
  }

  fun <T>read(collection: String) {
    Firebase.firestore.collection(collection)
      .get()
      .addOnSuccessListener { result ->
        result.forEach { document ->
          document.data
        }
      }
  }

  fun setDocument(collection: String, document: String, data: Any, onSuccess: () -> Unit) {
    Firebase.firestore.collection(collection).document(document)
      .set(data, SetOptions.merge())
      .addOnSuccessListener { onSuccess() }
      .addOnFailureListener { exception -> FireStoreResult.Error(exception) }
  }

  inline fun <reified T> readDocumentObject(collection: String, document: String, crossinline result: (FireStoreResult<T>) -> Unit) {
    Firebase.firestore.collection(collection).document(document).get()
      .addOnSuccessListener { documentSnapshot ->
        val obj = documentSnapshot.toObject<T>()
        obj?.let {
          result(FireStoreResult.Success(obj))
        }
      }.addOnFailureListener { exception ->
        result(FireStoreResult.Error(exception))
      }
  }
}