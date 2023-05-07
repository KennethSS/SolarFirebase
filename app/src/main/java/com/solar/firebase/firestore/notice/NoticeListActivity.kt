package com.solar.firebase.firestore.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.solar.firebase.databinding.ActivityNoticeListBinding
import com.solar.firebase.firestore.UiState
import com.solar.firebase.observeViewModel

class NoticeListActivity : AppCompatActivity() {
    private val binding by lazy { ActivityNoticeListBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<NoticeListViewModel>()
    private lateinit var adapter: NoticeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = NoticeListAdapter(viewModel)
        binding.noticeList.adapter = adapter
        observeViewModel(viewModel.stateFlow) { state ->
            when (state) {
                is UiState.Success<List<Notice>> -> adapter.submitList(state.data)
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, NoticeListActivity::class.java))
        }
    }
}
