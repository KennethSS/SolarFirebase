package com.solar.firebase.fcm

import android.os.Bundle
import androidx.activity.viewModels
import com.solar.firebase.R
import com.solar.firebase.databinding.ActivityMessageBinding
import com.solar.library.binding.activity.BindingActivity

class MessageActivity : BindingActivity() {

    private val binding by binding<ActivityMessageBinding>(R.layout.activity_message)
    private val viewModel by viewModels<MessageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.sendMessage.setOnClickListener {
            viewModel.sendMessage(
                am = resources.assets,
                "hihi")
        }
    }
}
