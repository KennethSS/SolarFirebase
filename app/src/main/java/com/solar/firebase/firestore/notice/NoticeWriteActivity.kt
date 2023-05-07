package com.solar.firebase.firestore.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.solar.firebase.databinding.ActivityNoticeWriteBinding
import com.solar.firebase.firestore.UiState
import com.solar.firebase.observeViewModel

class NoticeWriteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNoticeWriteBinding.inflate(layoutInflater) }
    private val vm by viewModels<NoticeWriteViewModel>()
    private val id by lazy { intent.getStringExtra("id").orEmpty() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = vm
        binding.lifecycleOwner = this
        vm.id = id
        observeViewModel(vm.uiState) { state ->
            when (state) {
                is UiState.Loading -> { }
                is UiState.Success<Any> -> finish()
            }
        }
        vm.loadNoticeDetail()
    }

    companion object {
        fun start(context: Context, id: String = "") {
            context.startActivity(
                Intent(context, NoticeWriteActivity::class.java).apply {
                    putExtra("id", id)
                }
            )
        }
    }
}
