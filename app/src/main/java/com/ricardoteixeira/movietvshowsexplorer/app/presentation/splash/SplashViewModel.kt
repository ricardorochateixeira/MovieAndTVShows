package com.ricardoteixeira.movietvshowsexplorer.app.presentation.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class SplashViewModel @ViewModelInject constructor(var mAuth: FirebaseAuth) : ViewModel() {

    var isLogged = false

    private val splashEventChannel = Channel<SplashEvent>()
    val splashEvent = splashEventChannel.receiveAsFlow()

    fun navigateToMoviesFragmentWithLogin() {
        viewModelScope.launch {
            delay(2000)
            splashEventChannel.send(SplashEvent.NavigateToMoviesFragmentWithLogin)
        }
    }

    fun navigateToWelcomeActivity() {
        viewModelScope.launch {
            delay(2000)
            splashEventChannel.send(SplashEvent.NavigateToWelcomeActivity)
        }
    }

    fun checkIfLoggedIn(): Boolean {

        val user = mAuth.currentUser
        isLogged = user != null
        return isLogged
    }

    sealed class SplashEvent {
        object NavigateToMoviesFragmentWithLogin : SplashEvent()
        object NavigateToWelcomeActivity : SplashEvent()
    }

}