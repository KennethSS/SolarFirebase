package com.solar.firebase.firestore.util

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseDatabaseListeners {

    fun readFirebaseRealtimeDatabase() {
        // Create Database Object
        val database = FirebaseDatabase.getInstance("<path of database>")
        val databaseRef = database.reference
        // Read from the database
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value
                Log.d("FirebaseDatabaseListeners", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("FirebaseDatabaseListeners", "Failed to read value.", error.toException())
            }
        })

    }
}