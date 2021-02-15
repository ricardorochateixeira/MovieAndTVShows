package com.ricardoteixeira.movietvshowsexplorer.app.presentation.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.MainActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class LoginViewModel @ViewModelInject constructor(
    private val mAuth: FirebaseAuth
): ViewModel() {

    private val loginEventChannel = Channel<LoginEvent>()
    val loginEvent = loginEventChannel.receiveAsFlow()

    fun login(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity()) { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch {
                            loginEventChannel.send(LoginEvent.NavigateToMoviesFragment)
                        }
                    } else {
                        viewModelScope.launch {
                            loginEventChannel.send(LoginEvent.ErrorLoggingIn("Problem logging in!"))
                        }
                    }
                }
        } else {
            viewModelScope.launch {
                loginEventChannel.send(LoginEvent.InsertPasswordOrEmail("Please insert email and password!"))
            }
        }
    }

    fun navigateToRegisterActivity() {
        viewModelScope.launch {
            loginEventChannel.send(LoginEvent.NavigateToRegisterActivity)
        }
    }

    fun enterAsAGuest() {
        viewModelScope.launch {
            loginEventChannel.send(LoginEvent.EnterAsAGuest)
        }
    }

    fun navigateToWelcomeActivity() {
        viewModelScope.launch {
            loginEventChannel.send(LoginEvent.NavigateToWelcomeActivity)
        }
    }

    fun navigateToMoviesFragment() {
        viewModelScope.launch {
            loginEventChannel.send(LoginEvent.NavigateToMoviesFragment)
        }
    }

    fun sendResetPasswordEmail(email: String) {

        if (email.isNotEmpty()) {

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        viewModelScope.launch {
                            loginEventChannel.send(LoginEvent.ResetPassword("Email sent!"))
                        }
                    }
                }
        } else {
            viewModelScope.launch {
                loginEventChannel.send(LoginEvent.InsertEmail("Please write email to receive a reset password email!"))
            }
        }
    }

    sealed class LoginEvent {
        object NavigateToMoviesFragment: LoginEvent()
        data class InsertPasswordOrEmail(val message: String): LoginEvent()
        data class ErrorLoggingIn(val message: String): LoginEvent()
        data class ResetPassword(val message: String): LoginEvent()
        data class InsertEmail(val message: String): LoginEvent()
        object NavigateToRegisterActivity: LoginEvent()
        object EnterAsAGuest: LoginEvent()
        object NavigateToWelcomeActivity: LoginEvent()
    }
}