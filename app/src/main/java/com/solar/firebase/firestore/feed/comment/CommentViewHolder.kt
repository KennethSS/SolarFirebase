package com.solar.firebase.firestore.feed.comment

import androidx.recyclerview.widget.RecyclerView
import com.solar.firebase.databinding.ItemCommentBinding

class CommentViewHolder(
    private val binding: ItemCommentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: Comment) {
        binding.comment = comment
    }
}
