package com.solar.firebase.database

import android.os.Bundle
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import com.sample.basefirebase.database.FirebaseDatabaseManager
import com.sample.basefirebase.database.recyclerview.FirebaseRecyclerViewAdapter
import com.solar.firebase.R
import com.solar.firebase.database.entity.comment.Comment
import com.solar.firebase.database.entity.comment.CommentViewHolder
import com.solar.firebase.extension.inflate
import com.solar.solarview.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_firebase_database.*
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentActivity : BaseActivity() {
    var adapter: FirebaseRecyclerAdapter<Comment, CommentViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_database)

        push.setOnClickListener {
            showProgress()
            val comment = Comment("1", "123", "45", "6", "&", 5.0f)
            FirebaseDatabaseManager.pushFirebase("comments", comment) {
                dismissProgress()
            }
        }

        loadCommentList("comments", 50)

        //FirebaseDatabase.getInstance().getReference()
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }


    private fun loadCommentList(reference: String, limit: Int) {
        val query = FirebaseDatabase.getInstance()
                .reference
                .child(reference)
                .limitToLast(limit)

        adapter = object: FirebaseRecyclerViewAdapter<Comment, CommentViewHolder>(query, Comment::class.java) {
            override fun createHolder(parent: ViewGroup): CommentViewHolder =
                    CommentViewHolder(parent.inflate(R.layout.item_comment))

            override fun bind(holder: CommentViewHolder, position: Int, model: Comment) {
                holder.itemView.comment.text = model.text
            }
        }

        database_list_view.adapter = adapter
    }
}