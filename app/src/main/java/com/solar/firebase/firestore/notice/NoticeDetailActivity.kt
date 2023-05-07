package com.solar.firebase.firestore.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.solar.firebase.databinding.ActivityNoticeDetailBinding

class NoticeDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNoticeDetailBinding.inflate(layoutInflater) }
    private val vm by viewModels<NoticeDetailViewModel>()
    private val id by lazy { intent?.getStringExtra("id").orEmpty() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = vm
        binding.lifecycleOwner = this
        vm.loadNoticeDetail(id)
    }

    companion object {
        fun start(context: Context, id: String) {
            context.startActivity(
                Intent(context, NoticeDetailActivity::class.java).apply {
                    putExtra("id", id)
                }
            )
        }
    }
}
