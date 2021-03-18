package com.ricardoteixeira.movietvshowsexplorer.domain.usecases.userprofile

import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.utils.ApiResponseHandler
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.utils.ApiResult
import com.ricardoteixeira.movietvshowsexplorer.app.utils.safeApiCall
import com.ricardoteixeira.movietvshowsexplorer.data.repository.userprofile.GetUserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.state.DataState
import com.ricardoteixeira.movietvshowsexplorer.domain.state.Response
import com.ricardoteixeira.movietvshowsexplorer.domain.usecases.common.BaseUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

const val GET_USER_PROFILE_SUCCESS = "User loaded successfully"
const val ERROR_GETTING_USER_PROFILE = "Error getting user profile"

class GetUserProfileUseCase @Inject constructor(private val getUserProfile: GetUserProfile): BaseUseCase<Unit, Flow<DataState<UserProfile?>?>> {

    override suspend fun invoke(params: Unit): Flow<DataState<UserProfile?>?> = flow {

        val apiResult = safeApiCall(IO) {
            getUserProfile.getUserProfile()
        }

        val response = object: ApiResponseHandler<UserProfile?>(apiResult) {
            override suspend fun handleSuccess(resultObj: UserProfile?): DataState<UserProfile?>? {
                return if (resultObj != null) {
                    DataState.data(response = Response(GET_USER_PROFILE_SUCCESS), data = resultObj)
                } else {
                    DataState.error(response = Response(ERROR_GETTING_USER_PROFILE))
                }
            }
        }.getResult()
        emit(response)
    }





}