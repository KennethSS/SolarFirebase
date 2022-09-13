package com.solar.firebase.firestore.feed.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.solar.firebase.databinding.ActivityFeedDetailBinding
import com.solar.firebase.firestore.feed.comment.CommentAdapter
import com.solar.firebase.firestore.feed.comment.CommentState
import com.solar.firebase.firestore.feed.comment.CommentViewModel
import com.solar.firebase.observeViewModel

class FeedDetailActivity : AppCompatActivity() {

    private val uid: String by lazy { "wggczOb5scphUZSSB8NV" } // intent.getStringExtra(KEY_UID).orEmpty() }
    private val binding by lazy { ActivityFeedDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<FeedDetailViewModel>()
    private val commentViewModel by viewModels<CommentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = viewModel
        binding.commentVm = commentViewModel
        binding.lifecycleOwner = this
        viewModel.loadFeedDetail(uid)
        commentViewModel.feedId = uid
        commentViewModel.loadComment(uid)

        observeViewModel(commentViewModel.commentsStateFlow) { state ->
            when (state) {
                is CommentState.Success -> {
                    binding.commentListView.adapter = CommentAdapter(state.comments)
                }
            }
        }
    }

    companion object {
        const val KEY_UID = "uid"
    }
}
