package com.ricardoteixeira.movietvshowsexplorer.data.repository.common

import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile

interface InsertOrUpdateUserProfile {
    suspend fun insertOrUpdateUserProfile(userProfile: UserProfile)
}