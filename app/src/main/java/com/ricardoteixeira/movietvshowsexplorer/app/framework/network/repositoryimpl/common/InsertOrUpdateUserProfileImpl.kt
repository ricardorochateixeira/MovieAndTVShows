package com.ricardoteixeira.movietvshowsexplorer.app.framework.network.repositoryimpl.common

import com.google.firebase.firestore.FirebaseFirestore
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.mappers.NetworkMapper
import com.ricardoteixeira.movietvshowsexplorer.app.utils.cLog
import com.ricardoteixeira.movietvshowsexplorer.data.repository.common.InsertOrUpdateUserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile
import javax.inject.Inject


class InsertOrUpdateUserProfileImpl @Inject constructor(private val firestore: FirebaseFirestore, private val networkMapper: NetworkMapper):
    InsertOrUpdateUserProfile {

    override suspend fun insertOrUpdateUserProfile(userProfile: UserProfile) {
        val userProfileFirebase = networkMapper.mapToEntity(userProfile)
        firestore.collection(USER_COLLECTION)
            .document(userProfileFirebase.id)
            .set(userProfileFirebase)
            .addOnFailureListener { cLog(it.message) }
    }

    companion object {
        const val USER_COLLECTION = "usersprofile"
    }
}