package com.ricardoteixeira.movietvshowsexplorer.app.presentation.loginregister

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.MainActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginRegisterViewModel
@ViewModelInject constructor(private val mAuth: FirebaseAuth,
private val mDatabase: DatabaseReference): ViewModel() {


    private val loginRegisterEventChannel = Channel<LoginRegisterEvent>()
    val loginRegisterEvent = loginRegisterEventChannel.receiveAsFlow()

    fun login(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity(), OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch {
                            loginRegisterEventChannel.send(LoginRegisterEvent.NavigateToMoviesScreen)
                        }
                    } else {
                        viewModelScope.launch {
                            loginRegisterEventChannel.send(LoginRegisterEvent.ErrorLoggingIn("Problem logging in!"))
                        }
                    }
                })
        } else {
            viewModelScope.launch {
                loginRegisterEventChannel.send(LoginRegisterEvent.InsertPasswordOrEmail("Please insert email and password!"))
            }
        }
    }

    fun registerUser(email: String, password: String, name: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity(), OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val uid = user!!.uid
                        mDatabase.child(uid).child("Name").setValue(name)
                        viewModelScope.launch {
                            loginRegisterEventChannel.send(LoginRegisterEvent.NavigateToMoviesScreen)
                        }
                    } else {
                        viewModelScope.launch {
                            loginRegisterEventChannel.send(LoginRegisterEvent.ErrorRegistering("Problem registering!"))
                        }
                    }
                })
        } else {
            viewModelScope.launch {
                loginRegisterEventChannel.send(LoginRegisterEvent.InsertPasswordOrEmail("Please insert email, password and name!"))
            }
        }
    }

    sealed class LoginRegisterEvent {
        object NavigateToMoviesScreen: LoginRegisterEvent()
        data class InsertPasswordOrEmail(val message: String): LoginRegisterEvent()
        data class ErrorLoggingIn(val message: String): LoginRegisterEvent()
        data class ErrorRegistering(val message: String): LoginRegisterEvent()
    }
}