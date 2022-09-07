package com.solar.firebase.auth.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.solar.firebase.databinding.ActivitySignUpBinding
import com.solar.firebase.extension.toastAndFinish
import com.solar.solarktx.toast
import com.solar.solarview.activity.BaseActivity
import kotlinx.coroutines.flow.collect

class SignUpActivity : BaseActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = viewModel
        lifecycleScope.launchWhenStarted {
            viewModel.signUpUiState.collect { state ->
                when (state) {
                    is SignUpUiState.Loading -> showProgress()
                    is SignUpUiState.Success -> toastAndFinish("회원가입이 완료되었습니다 로그인 해주세요")
                    is SignUpUiState.Error -> {
                        dismissProgress()
                        toast(state.msg)
                    }
                }
            }
        }
    }
}
