package com.ricardoteixeira.movietvshowsexplorer.app.presentation.common

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class FirebaseUserLiveData (private var mAuth: FirebaseAuth): LiveData<FirebaseUser?>(){

    private var authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        value = firebaseAuth.currentUser
    }

    override fun onActive() {
        mAuth.addAuthStateListener(authStateListener)
    }

    // When this object no longer has an active observer, stop observing the FirebaseAuth state to
    // prevent memory leaks.
    override fun onInactive() {
        mAuth.removeAuthStateListener(authStateListener)
    }

}