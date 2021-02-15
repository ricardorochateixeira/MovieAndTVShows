package com.ricardoteixeira.movietvshowsexplorer.app.presentation.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.MainActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(
    private val mAuth: FirebaseAuth,
    private val mDatabase: DatabaseReference
) : ViewModel() {

    val nameText = MutableStateFlow("")
    val passwordText = MutableStateFlow("")
    val passwordConfirmText = MutableStateFlow("")

    private val registerEventChannel = Channel<RegisterEvent>()
    val registerEvent = registerEventChannel.receiveAsFlow()

    private val nameTextFlow = nameText.flatMapLatest { name ->
        if (name.isBlank()) {
            flow<RegisterEvent> { registerEventChannel.send(RegisterEvent.HideCheckName) }
        } else {
            flow<RegisterEvent> { registerEventChannel.send(RegisterEvent.ShowCheckName) }
        }
    }

    private val passwordTextFlow = passwordText.flatMapLatest { password ->
        if (password.length > 8) {
            flow<RegisterEvent> { registerEventChannel.send(RegisterEvent.ShowCheckPassword) }
        } else {
            flow<RegisterEvent> { registerEventChannel.send(RegisterEvent.HideCheckPassword) }
        }
    }

    private val passwordConfirmTextFlow = passwordConfirmText.flatMapLatest { confirmPassword ->
        if (confirmPassword.length > 8) {
            flow<RegisterEvent> { registerEventChannel.send(RegisterEvent.ShowCheckConfirmPassword) }
        } else {
            flow<RegisterEvent> { registerEventChannel.send(RegisterEvent.HideCheckConfirmPassword) }
        }
    }

    val nameTextAsLiveData = nameTextFlow.asLiveData()
    val passwordTextAsLiveData = passwordTextFlow.asLiveData()
    val passwordConfirmTextAsLiveData = passwordConfirmTextFlow.asLiveData()

    fun registerUser(email: String, password: String, confirmPassword: String, name: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && name.isNotEmpty()) {
            if (password.length > 8) {
                if (password == confirmPassword) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity()) { task ->
                            if (task.isSuccessful) {
                                val user = mAuth.currentUser
                                val uid = user!!.uid
                                mDatabase.child(uid).child("Name").setValue(name)
                                viewModelScope.launch {
                                    registerEventChannel.send(RegisterEvent.NavigateToMoviesFragment)
                                }
                            } else {
                                viewModelScope.launch {
                                    registerEventChannel.send(RegisterEvent.ErrorRegistering("Problem registering!"))
                                }
                            }
                        }
                } else {
                    viewModelScope.launch {
                        registerEventChannel.send(RegisterEvent.PasswordNotEqualConfirmPassword("Please make sure that passwords are the same"))
                    }
                }
            } else {
                viewModelScope.launch {
                    registerEventChannel.send(RegisterEvent.PasswordLength("Password should have between 8 and 16 characters"))
                }
            }

        } else {
            viewModelScope.launch {
                registerEventChannel.send(RegisterEvent.InsertPasswordOrEmail("Please insert all the fields!"))
            }
        }
    }

    fun navigateToLoginActivity() {
        viewModelScope.launch {
            registerEventChannel.send(RegisterEvent.NavigateToLoginActivity)
        }
    }

    fun enterAsAGuest() {
        viewModelScope.launch {
            registerEventChannel.send(RegisterEvent.EnterAsAGuest)
        }
    }

    fun navigateToWelcomeActivity() {
        viewModelScope.launch {
            registerEventChannel.send(RegisterEvent.NavigateToWelcomeActivity)
        }
    }

    sealed class RegisterEvent {
        object NavigateToMoviesFragment : RegisterEvent()
        data class InsertPasswordOrEmail(val message: String) : RegisterEvent()
        data class ErrorRegistering(val message: String) : RegisterEvent()
        data class PasswordNotEqualConfirmPassword(val message: String) : RegisterEvent()
        data class PasswordLength(val message: String) : RegisterEvent()
        object NavigateToLoginActivity : RegisterEvent()
        object ShowCheckName : RegisterEvent()
        object HideCheckName : RegisterEvent()
        object ShowCheckPassword : RegisterEvent()
        object HideCheckPassword : RegisterEvent()
        object ShowCheckConfirmPassword : RegisterEvent()
        object HideCheckConfirmPassword : RegisterEvent()
        object EnterAsAGuest : RegisterEvent()
        object NavigateToWelcomeActivity : RegisterEvent()
    }
}