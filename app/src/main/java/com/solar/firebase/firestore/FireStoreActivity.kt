package com.solar.firebase.firestore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import com.solar.firebase.databinding.ActivityFireStoreBinding
import com.solar.firebase.firestore.feed.detail.FeedDetailActivity
import com.solar.firebase.firestore.feed.write.FeedWriteActivity

class FireStoreActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFireStoreBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.feedWrite.setOnClickListener { startActivity(Intent(this, FeedWriteActivity::class.java)) }
        binding.feedDetail.setOnClickListener { startActivity(Intent(this, FeedDetailActivity::class.java)) }
    }


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        write.setOnClickListener {
            *//*FireStoreManager
                .setDocument("collection 1", "document 1", sample2())*//*
        }

        read.setOnClickListener {
            FireStoreManager.readDocumentObject<City>("collection 1", "document 1") {
                when (it) {
                    is FireStoreResult.Success<City> -> {
                        println(it.data)
                    }
                    is FireStoreResult.Error -> {
                        println(it.throwable)
                    }
                }
            }
        }
    }

    private fun sample2(): HashMap<String, Any> {
        return hashMapOf(
            "array" to List(10) { sample() }
        )
    }

    private fun sample(): HashMap<String, Any?> {
        return hashMapOf(
            "stringExample" to "Hello world!",
            "booleanExample" to true,
            "numberExample" to 3.14159265,
            "dateExample" to Timestamp(Date()),
            "listExample" to arrayListOf(1, 2, 3),
            "nullExample" to null
        )
    }*/

    data class City(
        @SerializedName("name")
        val name: String = ""
    )
}
