package com.solar.firebase.auth.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.solar.firebase.auth.signup.SignUpActivity
import com.solar.firebase.databinding.ActivitySignInBinding
import com.solar.solarktx.toast
import com.solar.solarview.activity.BaseActivity
import kotlinx.coroutines.flow.collect

class SignInActivity : BaseActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = signInViewModel
        binding.lifecycleOwner = this
        binding.btnSignUp.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }
        lifecycleScope.launchWhenStarted {
            signInViewModel.signInUiState.collect { state ->
                when (state) {
                    is SignInUiState.Loading -> showProgress()
                    is SignInUiState.SuccessSignIn -> { dismissProgress() }
                    is SignInUiState.ErrorSignIn -> {
                        toast(state.msg)
                        dismissProgress()
                    }
                }
            }
        }
    }

}
