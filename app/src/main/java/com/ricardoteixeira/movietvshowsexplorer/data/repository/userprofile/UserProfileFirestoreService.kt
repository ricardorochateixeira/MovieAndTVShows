package com.ricardoteixeira.movietvshowsexplorer.data.repository.userprofile

import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow


interface GetUserProfile {
    suspend fun getUserProfile(): UserProfile?
}

interface DeleteUserProfile {
    suspend fun deleteUserProfile(userProfile: UserProfile)
}