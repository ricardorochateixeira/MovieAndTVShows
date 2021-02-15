package com.ricardoteixeira.movietvshowsexplorer.app.presentation.welcome

import android.os.Handler
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.map
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.common.FirebaseUserLiveData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WelcomeViewModel
@ViewModelInject constructor(): ViewModel() {

    private val welcomeEventChannel = Channel<WelcomeEvent>()
    val welcomeEvent = welcomeEventChannel.receiveAsFlow()

    fun navigateToLoginActivity() {
        viewModelScope.launch {
            welcomeEventChannel.send(WelcomeEvent.NavigateToLoginActivity)
        }
    }

    fun navigateToRegisterActivity() {
        viewModelScope.launch {
            welcomeEventChannel.send(WelcomeEvent.NavigateToRegisterActivity)
        }
    }

    fun enterASAGuest() {
        viewModelScope.launch {
            welcomeEventChannel.send(WelcomeEvent.EnterAsAGuest)
        }
    }

    sealed class WelcomeEvent {
        object NavigateToLoginActivity: WelcomeEvent()
        object NavigateToRegisterActivity: WelcomeEvent()
        object EnterAsAGuest: WelcomeEvent()
        object NavigateToMoviesFragmentWithLogin: WelcomeEvent()
    }

}