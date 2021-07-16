package com.solar.firebase.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.solar.firebase.R

import com.solar.firebase.auth.AuthActivity
import com.solar.firebase.crashlytics.CrashlyticsActivity
import com.solar.firebase.database.CommentActivity
import com.solar.firebase.storage.StorageActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebase_auth.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }

        firebase_database_list.setOnClickListener {
            startActivity(Intent(this, CommentActivity::class.java))
        }

        firebase_storage.setOnClickListener {
            startActivity(Intent(this, StorageActivity::class.java))
        }



        location.setOnClickListener { startActivity(Intent(this, LocationActivity::class.java)) }

        view_pager.setOnClickListener {
            startActivity(Intent(this, SelectActivity::class.java))
        }

        crashlytics.setOnClickListener {
            startActivity(Intent(this, CrashlyticsActivity::class.java))
        }
    }
}
