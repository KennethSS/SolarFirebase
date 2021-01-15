package com.solar.firebase.database

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sample.basefirebase.database.FirebaseDatabaseManager
import com.sample.basefirebase.storage.FirebaseStorageManager
import com.sample.basefirebase.database.recyclerview.FirebaseRecyclerViewAdapter
import com.solar.firebase.R
import com.solar.firebase.database.entity.comment.Comment
import com.solar.firebase.database.entity.comment.CommentViewHolder
import com.solar.firebase.extension.inflate
import com.solar.solarview.activity.BaseActivity
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.activity_firebase_database.*
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentActivity : BaseActivity() {
    var adapter: FirebaseRecyclerAdapter<Comment, CommentViewHolder>? = null

    private var uri: Uri? = null

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

        upload_img.setOnClickListener {
            uri?.let {
                showProgress()
                FirebaseStorageManager.upload("test", it,  {
                    Log.d("CommentActivity", it.toString())
                    dismissProgress()
                }, {

                })
            }

        }
        get_image.setOnClickListener {
            TedPermission.with(this)
                    .setPermissionListener(object: PermissionListener {
                        override fun onPermissionGranted() {
                            Matisse.from(this@CommentActivity)
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


        loadCommentList("comments", 50)

        //FirebaseDatabase.getInstance().getReference()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val images = Matisse.obtainResult(data)

                if (images.isNotEmpty()) {
                    this.uri = images[0]
                    Glide.with(comment_img)
                            .load(images[0])
                            .into(comment_img)
                }
            }
        }
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