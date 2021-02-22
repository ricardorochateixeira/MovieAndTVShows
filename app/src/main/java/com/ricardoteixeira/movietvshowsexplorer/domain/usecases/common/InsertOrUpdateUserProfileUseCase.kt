package com.ricardoteixeira.movietvshowsexplorer.domain.usecases.common

import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.data.repository.common.InsertOrUpdateUserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile
import javax.inject.Inject

class InsertOrUpdateUserProfileUseCase
    @Inject constructor(private val insertOrUpdateUserProfile: InsertOrUpdateUserProfile): BaseUseCase<UserProfile, Unit> {

    override suspend fun invoke(params: UserProfile) {
        insertOrUpdateUserProfile.insertOrUpdateUserProfile(params)
    }
}