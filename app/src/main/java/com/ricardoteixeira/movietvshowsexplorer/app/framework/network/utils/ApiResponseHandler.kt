package com.ricardoteixeira.movietvshowsexplorer.app.framework.network.utils

import com.ricardoteixeira.movietvshowsexplorer.app.framework.db.utils.CacheErrors
import com.ricardoteixeira.movietvshowsexplorer.app.framework.db.utils.CacheResult
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.utils.NetworkErrors.NETWORK_DATA_NULL
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.utils.NetworkErrors.NETWORK_ERROR
import com.ricardoteixeira.movietvshowsexplorer.domain.state.DataState
import com.ricardoteixeira.movietvshowsexplorer.domain.state.Response

abstract class ApiResponseHandler<Data>(private val response: ApiResult<Data>) {

    suspend fun getResult(): DataState<Data>? {
        return when (response) {

            is ApiResult.GenericError -> {
                DataState.error(
                    Response("Reason: ${response.errorMessage}")
                )
            }


            is ApiResult.NetworkError -> {
                DataState.error(Response("Reason ${NETWORK_ERROR}"))
            }

            is ApiResult.Success -> {
                if (response.value == null) {
                    DataState.error(
                        Response("Reason: ${NETWORK_DATA_NULL}")
                    )
                } else {
                    handleSuccess(response.value)
                }
            }
        }
    }
    abstract suspend fun handleSuccess(resultObj: Data): DataState<Data>?
}