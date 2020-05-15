package com.solar.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.solar.firebase.database.CommentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //FirebaseDatabase.getInstance().reference.setValue("HIHI")
        auth_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        location.setOnClickListener { startActivity(Intent(this, LocationActivity::class.java)) }
        view_pager.setOnClickListener {
            startActivity(Intent(this, SelectActivity::class.java))
        }

        firebase_database_list.setOnClickListener {
            startActivity(Intent(this, CommentActivity::class.java))
        }
    }
}
