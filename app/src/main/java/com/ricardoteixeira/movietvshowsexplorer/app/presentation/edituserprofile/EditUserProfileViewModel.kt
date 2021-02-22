package com.ricardoteixeira.movietvshowsexplorer.app.presentation.edituserprofile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.app.utils.Data
import com.ricardoteixeira.movietvshowsexplorer.app.utils.Status
import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.usecases.common.InsertOrUpdateUserProfileUseCase
import com.ricardoteixeira.movietvshowsexplorer.domain.usecases.userprofile.GetUserProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditUserProfileViewModel @ViewModelInject
constructor(
    private val mAuth: FirebaseAuth,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val insertOrUpdateUserProfileUseCase: InsertOrUpdateUserProfileUseCase,
) : ViewModel() {

    private val editUserProfileEventChannel = Channel<EditUserProfileEvent>()
    val userProfileEvent = editUserProfileEventChannel.receiveAsFlow()

    private var _mutableLiveDataImageProfileUri = MutableLiveData<String>()

    private var _mutableLiveDataUserName = MutableLiveData<String>()

    private val _mutableEditUserState: MutableLiveData<Data<UserProfile>> = MutableLiveData()
    val userEditState: LiveData<Data<UserProfile>>
        get() = _mutableEditUserState

    init {
        _mutableEditUserState.value = Data(isLoading = true)
            viewModelScope.launch(Dispatchers.IO) {
                getUserProfileUseCase(Unit).collect { userProfile ->
                    _mutableEditUserState.postValue(Data(isLoading = false, responseType = Status.SUCCESSFUL, data = userProfile))
                }
            }
    }

    fun saveUserProfile(userName: String) {
        _mutableLiveDataUserName.value = userName
            viewModelScope.launch {
                getUserProfileUseCase(Unit).collect { userProfile ->
                    if (_mutableLiveDataImageProfileUri.value != null && _mutableLiveDataUserName.value != null) {
                        insertOrUpdateUserProfileUseCase(userProfile!!.copy(name = _mutableLiveDataUserName.value!!, imageUri = _mutableLiveDataImageProfileUri.value!!))
                    } else if (_mutableLiveDataImageProfileUri.value == null && _mutableLiveDataUserName.value != null) {
                        insertOrUpdateUserProfileUseCase(userProfile!!.copy(name = userName))
                    }
                }
        }
    }

    fun setImageUri(imageUri: String) {
        _mutableLiveDataImageProfileUri.value = imageUri
    }

    fun cancelChanges() {
        viewModelScope.launch {
            _mutableEditUserState.value?.copy(event = editUserProfileEventChannel.send(EditUserProfileEvent.AreYouSureYouWantToCancel("Do you want to leave without save changes?")))
        }
    }

    fun saveChanges() {
        viewModelScope.launch {
            _mutableEditUserState.value?.copy(event = editUserProfileEventChannel.send(EditUserProfileEvent.DoYouWantToSaveChanges("Do you want to save changes?")))
        }
    }

    fun navigateToUserProfileActivityWithoutSaving(){
        viewModelScope.launch {
            _mutableEditUserState.value?.copy(event = editUserProfileEventChannel.send(EditUserProfileEvent.NavigateToUserProfileActivity))
        }
    }

    fun navigateToUserProfileActivitySaving() {
        viewModelScope.launch {
            _mutableEditUserState.value?.copy(event = editUserProfileEventChannel.send(EditUserProfileEvent.NavigateToUserProfileActivity))
        }
    }

    sealed class EditUserProfileEvent {
        data class AreYouSureYouWantToCancel(val message: String): EditUserProfileEvent()
        data class DoYouWantToSaveChanges(val message: String): EditUserProfileEvent()
        object NavigateToUserProfileActivity: EditUserProfileEvent()
    }
}