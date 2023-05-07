package com.solar.firebase.firestore

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.solar.firebase.observeViewModel

abstract class AbstractActivity(
    private val title: String,
    private val vm: AbstractViewModel,
    private val isTwiceBackPressClose: Boolean = false
) : AppCompatActivity() {
    private var backPressTime: Long = 0L

    private val dialog: AppCompatDialog by lazy { getAppCompatDialog() }
    private val dialogHandler: Handler by lazy { Handler() }
    private val dialogRunnable = Runnable { dialog.dismiss() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = title
        observeViewModel(vm.stateFlow) { state ->
            when (state) {
                is UiState.Loading -> showProgress()
                //is UiState.Error -> toast(state.error.message.orEmpty())
            }
        }
    }

    override fun onBackPressed() {
        if (isTwiceBackPressClose) {
            if (System.currentTimeMillis() - backPressTime >= 2000) {
                backPressTime = System.currentTimeMillis()
                Toast
                    .makeText(this, "뒤로 버튼을 한 더 누르면 종료합니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    fun showProgress() {
        if (isFinishing) dialogHandler.postDelayed(
            dialogRunnable,
            TIMEOUT_SECOND * MILLION_SECOND
        )
        else dialog.show()
    }

    fun dismissProgress() {
        if (isFinishing) dialogHandler.removeCallbacks(dialogRunnable)
        else dialog.dismiss()
    }

    private fun getAppCompatDialog() = AppCompatDialog(this).apply {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val progressBar = ProgressBar(context).apply {
            isIndeterminate = true
        }

        setContentView(progressBar)
    }

    companion object {
        private const val TIMEOUT_SECOND = 30
        private const val MILLION_SECOND = 1000L
    }
}
