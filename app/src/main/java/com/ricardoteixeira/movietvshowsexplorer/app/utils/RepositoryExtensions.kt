package com.ricardoteixeira.movietvshowsexplorer.app.utils

import com.ricardoteixeira.movietvshowsexplorer.app.framework.db.utils.CacheErrors.CACHE_ERROR_TIMEOUT
import com.ricardoteixeira.movietvshowsexplorer.app.framework.db.utils.CacheErrors.CACHE_ERROR_UNKNOWN
import com.ricardoteixeira.movietvshowsexplorer.app.framework.db.utils.CacheResult
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.utils.ApiResult
import com.ricardoteixeira.movietvshowsexplorer.app.utils.GenericErrors.ERROR_UNKNOWN
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.utils.NetworkErrors.NETWORK_ERROR_TIMEOUT
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.utils.NetworkErrors.NETWORK_ERROR_UNKNOWN
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

const val NETWORK_TIMEOUT = 6000L
const val CACHE_TIMEOUT = 2000L

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
apiCall: suspend () -> T?): ApiResult<T?> {

    return withContext(dispatcher) {
        try {
            withTimeout(NETWORK_TIMEOUT) {
                ApiResult.Success(apiCall.invoke())
            }
        } catch (throwable: Throwable){
            cLog(throwable.message)
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 500
                    ApiResult.GenericError(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    ApiResult.NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ApiResult.GenericError(code, errorResponse)
                }
                else -> {
                    ApiResult.GenericError(null, NETWORK_ERROR_UNKNOWN)
                }
            }
        }
    }
}

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend() -> T?): CacheResult<T?> {

    return withContext(dispatcher) {
        try {
            withTimeout(CACHE_TIMEOUT) {
                CacheResult.Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            cLog(throwable.message)
            throwable.printStackTrace()
            when(throwable){
                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else -> {
                    CacheResult.GenericError(CACHE_ERROR_UNKNOWN)
                }
            }
        }
    }
}


private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        ERROR_UNKNOWN
    }
}