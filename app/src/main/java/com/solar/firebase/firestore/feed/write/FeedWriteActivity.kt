package com.solar.firebase.firestore.feed.write

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.solar.firebase.databinding.ActivityFeedBinding
import com.solar.firebase.firestore.FireStoreState
import com.solar.firebase.observeViewModel
import com.solar.solarktx.toast

class FeedWriteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFeedBinding.inflate(layoutInflater) }
    private val feedWriteViewModel by viewModels<FeedWriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = feedWriteViewModel
        observeViewModel(feedWriteViewModel.fireStoreState) { state ->
            when (state) {
                is FireStoreState.Loading -> { }
                is FireStoreState.Success -> finish()
                is FireStoreState.Error -> toast(state.msg)
            }
        }
    }
}
