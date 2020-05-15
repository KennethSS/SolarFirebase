package com.sample.basefirebase.database.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query

abstract class FirebaseRecyclerViewAdapter<T, H : RecyclerView.ViewHolder>(
        private val query: Query,
        private val clazz: Class<T>,
        options: FirebaseRecyclerOptions<T> = FirebaseRecyclerOptions.Builder<T>()
                .setQuery(query) { snapshot -> snapshot.getValue(clazz) as T }
                .build()
)
    : FirebaseRecyclerAdapter<T, H>(options) {


    protected abstract fun createHolder(parent: ViewGroup): H
    protected abstract fun bind(holder: H, position: Int, model: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H = createHolder(parent)

    override fun onBindViewHolder(holder: H, position: Int, model: T) { bind(holder, position, model) }
}