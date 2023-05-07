package com.solar.firebase.auth.signin

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel : ViewModel() {

    val email = MutableStateFlow("")
    val pw = MutableStateFlow("")

    private val _signInUiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState.Idle)
    val signInUiState: StateFlow<SignInUiState> = _signInUiState

    fun signIn() {
        if (email.value.isEmpty() && pw.value.isEmpty()) {
            _signInUiState.value = SignInUiState.ErrorSignIn("이메일과 패스워드를 입력해주세요")
        } else {
            _signInUiState.value = SignInUiState.Loading
            Firebase.auth.signInWithEmailAndPassword(EMAIL, PW)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        getUserLogging()
                        val user = Firebase.auth.currentUser
                        _signInUiState.value = SignInUiState.SuccessSignIn(user)
                    } else {
                        println("Exception: ${task.exception}")
                        _signInUiState.value =
                            SignInUiState.ErrorSignIn("Failed Sign In: ${task.exception}")
                    }
                }
        }
    }

    fun signOut() {

    }

    fun sendEmailForFindPassword() {
        if (email.value.isEmpty()) _signInUiState.value = SignInUiState.ErrorSignIn("이메일을 입력해 주세요")
        else {
            _signInUiState.value = SignInUiState.ErrorSignIn("비밀번호 재설정 메일을 보냈습니다. 메일을 확인해 주세요")
            Firebase.auth.sendPasswordResetEmail(email.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("Email sent")
                    }
                }
        }
    }

    private fun getUserLogging() {
        val user = Firebase.auth.currentUser
        println("User UID: ${user?.uid}")
        println("User Name: ${user?.displayName}")
        println("User Email: ${user?.email}")
        println("User PhoneNumber: ${user?.phoneNumber}")
        println("User isAnonymous: ${user?.isAnonymous}")
    }

    companion object {
        const val EMAIL = "wlsrhkd4024@naver.com"
        const val PW = "rhrnak40"
    }
}
