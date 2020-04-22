package com.solar.firebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.dialog_pop_shop_category_select.*

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_pop_shop_category_select)

        supportFragmentManager?.let {
            category_select_view_pager.adapter = CategoryPagerAdapter(it,
                    listOf(
                            CategoryFragment.newInstance(),
                            CategoryFragment.newInstance()
                    ))
        }
    }
}