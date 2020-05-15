package com.sample.basefirebase.database

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseDatabaseManager  {
    fun <T>pushFirebase(reference: String, value: T, complete: (Task<Void>) -> Unit) {
        FirebaseDatabase
                .getInstance()
                .getReference(reference)
                .push()
                .setValue(value)
                .addOnCompleteListener(complete)
    }

    fun <T> getListFromFirebase(reference: String, valueType: Class<T>, result: (list: List<T>) -> Unit) {
        FirebaseDatabase
                .getInstance()
                .getReference(reference)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) { result(listOf()) }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val array = arrayListOf<T>()
                        snapshot.children.forEach {
                            it.getValue(valueType)?.let {
                                array.add(it)
                            }
                        }
                        result(array)
                    }
                })
    }
}