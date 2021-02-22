package com.ricardoteixeira.movietvshowsexplorer.app.presentation.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.common.FirebaseUserLiveData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private var mAuth: FirebaseAuth) : ViewModel() {

    var authenticationState = FirebaseUserLiveData(mAuth).map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    private val mainEventChannel = Channel<MainEvent>()
    val mainEvent = mainEventChannel.receiveAsFlow()


    fun showDoYouWantToLogOutDialog() {
        viewModelScope.launch {
            mainEventChannel.send(MainEvent.ShowDoYouWantToLogOutDialog("Do you want to Log out?"))
        }
    }

    fun logOut() {
        mAuth.signOut()
    }

    fun showDoYouWQWantToNavigateToLoginPage() {
        viewModelScope.launch {
            mainEventChannel.send(MainEvent.ShowDoYouWQWantToNavigateToLoginPage("Do you want to navigate to Log In page?"))
        }
    }

    fun navigateToUserFragment() {
        viewModelScope.launch {
            mainEventChannel.send(MainEvent.NavigateToUserFragment)
        }
    }

    enum class AuthenticationState {
        AUTHENTICATED,
        UNAUTHENTICATED
    }

    sealed class MainEvent {
        data class ShowDoYouWantToLogOutDialog(val message: String) : MainEvent()
        data class ShowDoYouWQWantToNavigateToLoginPage(val message: String): MainEvent()
        object NavigateToUserFragment: MainEvent()
    }

}