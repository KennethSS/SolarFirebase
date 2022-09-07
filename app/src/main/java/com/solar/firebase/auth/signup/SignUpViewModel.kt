package com.solar.firebase.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.solar.firebase.auth.signin.SignInViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val pwCheck = MutableLiveData<String>()
    val nickName = MutableLiveData<String>()

    private val _signUpUiState: MutableSharedFlow<SignUpUiState> =
        MutableStateFlow(SignUpUiState.Idle)
    val signUpUiState = _signUpUiState.asSharedFlow()

    fun signUp() {
        viewModelScope.launch { _signUpUiState.emit(SignUpUiState.Loading) }

        Firebase.auth.createUserWithEmailAndPassword(SignInViewModel.EMAIL, SignInViewModel.PW)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
                        _signUpUiState.emit(SignUpUiState.Success(user))
                    } else {
                        when (task.exception) {
                            is FirebaseAuthUserCollisionException -> showErrorToast("회원가입 할 수 없는 계정입니다. 다른 계정으로 시도해 주세요")
                            is FirebaseAuthWeakPasswordException -> showErrorToast("비밀번호가 보안에 취약합니다. 다시 작성해주세요")
                        }
                    }
                }
            }
    }

    private suspend fun showErrorToast(msg: String) {
        _signUpUiState.emit(SignUpUiState.Error(msg))
    }
}
