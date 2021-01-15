package com.solar.firebase.storage

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sample.basefirebase.storage.FirebaseStorageManager
import com.sample.basefirebase.storage.image.setStorageImage
import com.solar.firebase.MainActivity
import com.solar.firebase.R
import com.solar.firebase.databinding.ActivityStorageBinding
import com.solar.library.binding.activity.BindingActivity
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine

/**
 *  Created by Kenneth on 12/22/20
 */
class StorageActivity : BindingActivity() {

    private val binding by binding<ActivityStorageBinding>(R.layout.activity_storage)

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.storageImg.setStorageImage("SubFolder1/34.jpg")
        binding.setCurrentImg.setOnClickListener { getImage() }
        binding.upload.setOnClickListener {



            startService(Intent(this, FileUploadService::class.java).apply {
                putExtra("reference", "test")
                putExtra("uri", uri!!)
            })
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val images = Matisse.obtainResult(data)

                if (images.isNotEmpty()) {
                    this.uri = images[0]
                    Glide.with(binding.storageImg)
                            .load(images[0])
                            .into(binding.storageImg)
                }
            }
        }
    }

    private fun getImage() {
        TedPermission.with(this)
                .setPermissionListener(object: PermissionListener {
                    override fun onPermissionGranted() {
                        Matisse.from(this@StorageActivity)
                                .choose(MimeType.ofImage())
                                .countable(true)
                                .maxSelectable(1)
                                //.addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                //.gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f)
                                .imageEngine(GlideEngine())
                                //.showPreview(false) // Default is `true
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
}