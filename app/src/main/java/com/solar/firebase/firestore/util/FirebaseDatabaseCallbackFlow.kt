package com.solar.firebase.firestore.util

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object FirebaseDatabaseCallbackFlow {

    fun <T> readFirebaseRealtimeDatabaseFlow(path: String): Flow<T> = callbackFlow {
        val database = FirebaseDatabase.getInstance(path)
        val databaseRef = database.reference

        val firebaseDataListeners = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value
                Log.d("FirebaseDatabaseCallbackFlow", "Value is: $value")
                trySend(value as T)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FirebaseDatabaseCallbackFlow", "Failed to read value.", error.toException())
            }
        }

        databaseRef.addValueEventListener(firebaseDataListeners)

        awaitClose {
            databaseRef.removeEventListener(firebaseDataListeners)
        }
    }

    fun <T> setFirebaseDatabase(reference: String, value: T, complete: (Task<Void>) -> Unit) {
        FirebaseDatabase
            .getInstance()
            .getReference(reference)
            .push()
            .setValue(value)
            .addOnCompleteListener(complete)
    }
}
