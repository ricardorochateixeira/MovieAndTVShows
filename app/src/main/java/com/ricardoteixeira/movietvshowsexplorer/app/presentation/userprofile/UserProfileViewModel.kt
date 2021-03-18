package com.ricardoteixeira.movietvshowsexplorer.app.presentation.userprofile

import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.edituserprofile.URIPathHelper
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.login.LoginViewModel
import com.ricardoteixeira.movietvshowsexplorer.app.utils.Data
import com.ricardoteixeira.movietvshowsexplorer.app.utils.Status
import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.usecases.userprofile.GetUserProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class UserProfileViewModel @ViewModelInject
constructor(private val mAuth: FirebaseAuth,
            private val getUserProfileUseCase: GetUserProfileUseCase
): ViewModel(){

    private var isLogged = false

    private val userProfileEventChannel = Channel<UserProfileEvent>()
    val userProfileEvent = userProfileEventChannel.receiveAsFlow()

    private val _mutableUserState: MutableLiveData<Data<UserProfile>> = MutableLiveData()
    val userState: LiveData<Data<UserProfile>>
        get() = _mutableUserState

    init {
        _mutableUserState.value = Data(isLoading = true)
    }

    fun initProfile() {
        checkIfLoggedIn()
        if (mAuth.currentUser != null) {
            viewModelScope.launch(Dispatchers.IO) {
                getUserProfileUseCase(Unit).collect { userProfile ->
                    _mutableUserState.postValue(Data(isLoading = false, responseType = Status.SUCCESSFUL, data = userProfile!!.data))
                }
            }
        } else {
            _mutableUserState.postValue(Data(isLoading = false, responseType = Status.SUCCESSFUL, data = UserProfile(name = "there", id = "", email = "", dateCreated = "", imageUri = "")))
        }
    }

    private fun checkIfLoggedIn(): Boolean {
        var user = mAuth.currentUser
        isLogged = user != null
        return isLogged
    }

    fun handleClickEditButton() {
        if (isLogged) {
            viewModelScope.launch {
                _mutableUserState.value?.copy(event = userProfileEventChannel.send(UserProfileEvent.NavigateToEditUserProfileActivity))
            }
        } else {
            viewModelScope.launch {
                _mutableUserState.value?.copy(event = userProfileEventChannel.send(UserProfileEvent.ShowYouAreNotLoggedSnackBar("You are not logged in!")))
            }
        }
    }

    fun navigateToMainActivity() {
        viewModelScope.launch {
            _mutableUserState.value?.copy(event = userProfileEventChannel.send(UserProfileEvent.NavigateToMainActivity))
        }
    }

    sealed class UserProfileEvent {
        object NavigateToEditUserProfileActivity: UserProfileEvent()
        object NavigateToMainActivity: UserProfileEvent()
        data class ShowYouAreNotLoggedSnackBar(val message: String): UserProfileEvent()
    }
}