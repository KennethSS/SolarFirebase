package com.solar.firebase.firestore.feed.write

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.solar.firebase.R
import com.solar.firebase.databinding.ActivityFeedBinding
import com.solar.firebase.firestore.FireStoreState
import com.solar.firebase.observeViewModel
import com.solar.solarktx.toast
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine

class FeedWriteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFeedBinding.inflate(layoutInflater) }
    private val feedWriteViewModel by viewModels<FeedWriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = feedWriteViewModel
        binding.camera.setOnClickListener { getImage() }
        binding.pictureClose.setOnClickListener {
            feedWriteViewModel.uri = null
            binding.camera.isVisible = false
            binding.pictureClose.isVisible = false
        }
        observeViewModel(feedWriteViewModel.fireStoreState) { state ->
            when (state) {
                is FireStoreState.Idle -> { }
                is FireStoreState.Loading -> { }
                is FireStoreState.Success -> finish()
                is FireStoreState.Error -> toast(state.msg)
            }
        }
    }

    private fun getImage() {
        TedPermission.with(this)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    Matisse.from(this@FeedWriteActivity)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(GlideEngine())
                        .theme(R.style.Matisse_Dracula)
                        .forResult(0)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                }
            })
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val images = Matisse.obtainResult(data)

                if (images.isNotEmpty()) {
                    binding.picture.isVisible = true
                    binding.pictureClose.isVisible = true
                    feedWriteViewModel.uri = images[0]
                    Glide.with(binding.picture)
                        .load(images[0])
                        .into(binding.picture)
                }
            }
        }
    }
}
