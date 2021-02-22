package com.ricardoteixeira.movietvshowsexplorer.domain.usecases.userprofile

import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.data.repository.userprofile.GetUserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.usecases.common.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val getUserProfile: GetUserProfile): BaseUseCase<Unit, Flow<UserProfile?>> {

    override suspend fun invoke(params: Unit): Flow<UserProfile?> = getUserProfile.getUserProfile()


}