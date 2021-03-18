package com.ricardoteixeira.movietvshowsexplorer.app.framework.db.utils

import com.ricardoteixeira.movietvshowsexplorer.app.framework.db.utils.CacheErrors.CACHE_DATA_NULL
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.utils.ApiResult
import com.ricardoteixeira.movietvshowsexplorer.domain.state.DataState
import com.ricardoteixeira.movietvshowsexplorer.domain.state.Response

abstract class CacheResponseHandler<Data, Return>(private val response: CacheResult<Data>) {

    suspend fun getResult(): DataState<Data>? {
        return when (response) {

            is CacheResult.GenericError -> {
                DataState.error(
                    Response("Reason: ${response.errorMessage}")
                )
            }

            is CacheResult.Success -> {
                if (response.value == null) {
                    DataState.error(
                        Response("Reason: ${CACHE_DATA_NULL}")
                    )
                } else {
                    handleSuccess(response.value)
                }
            }
        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): DataState<Data>?
}