package com.solar.firebase.auth.signup

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sample.basefirebase.database.FirebaseDatabaseManager
import com.solar.firebase.auth.user.User
import com.solar.firebase.firestore.FireStoreManager
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
        if (checkAvailableSignUp()) {
            if (email.value != null && pw.value != null) {
                viewModelScope.launch { _signUpUiState.emit(SignUpUiState.Loading) }
                Firebase.auth.createUserWithEmailAndPassword(email.value!!, pw.value!!)
                    .addOnCompleteListener { task ->
                        viewModelScope.launch {
                            if (task.isSuccessful) {
                                Firebase.auth.currentUser?.let { user ->
                                    saveUserData(user.uid, User(nickName.value))
                                    _signUpUiState.emit(SignUpUiState.Success(user))
                                }
                            } else {
                                when (task.exception) {
                                    is FirebaseAuthUserCollisionException -> showErrorToast("회원가입 할 수 없는 계정입니다. 다른 계정으로 시도해 주세요")
                                    is FirebaseAuthWeakPasswordException -> showErrorToast("비밀번호가 보안에 취약합니다. 다시 작성해주세요")
                                }
                            }
                        }
                    }
            } else {
                viewModelScope.launch { showErrorToast("이메일 혹은 비밀번호가 잘못되었습니다.") }
            }
        } else {
            viewModelScope.launch { showErrorToast("이메일 혹은 비밀번호가 잘못되었습니다.") }
        }
    }

    private fun saveUserData(uid: String, user: User) {
        FireStoreManager.setDocument("users", uid, user, { }, { })
    }

    private suspend fun showErrorToast(msg: String) {
        _signUpUiState.emit(SignUpUiState.Error(msg))
    }

    private fun checkAvailableSignUp(): Boolean {
        return confirmPasswordMatch() && isValidateEmail()
    }

    private fun isValidateEmail() = Patterns.EMAIL_ADDRESS.matcher(email.value ?: "").matches()

    private fun confirmPasswordMatch() = pw.value == pwCheck.value
}
