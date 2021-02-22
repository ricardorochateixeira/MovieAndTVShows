package com.ricardoteixeira.movietvshowsexplorer.app.framework.network.repositoryimpl.userprofile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.UserProfileFirestore
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.mappers.NetworkMapper
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.repositoryimpl.common.InsertOrUpdateUserProfileImpl
import com.ricardoteixeira.movietvshowsexplorer.app.utils.cLog
import com.ricardoteixeira.movietvshowsexplorer.data.repository.userprofile.GetUserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetUserProfileImpl @Inject constructor(
    private val mAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val networkMapper: NetworkMapper
) : GetUserProfile {
    override suspend fun getUserProfile(): Flow<UserProfile?> = flow {
            val userProfileId = mAuth.currentUser!!.uid
            val userProfile = firestore.collection(InsertOrUpdateUserProfileImpl.USER_COLLECTION)
                .document(userProfileId)
                .get()
                .addOnFailureListener { println("error") }
                .await().toObject(UserProfileFirestore::class.java)?.let {
                    networkMapper.mapFromEntity(it)
                }
        emit(userProfile)
        }
}
