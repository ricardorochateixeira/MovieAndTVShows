package com.ricardoteixeira.movietvshowsexplorer.app.utils

import kotlinx.coroutines.channels.Channel

data class Data<T>(var isLoading: Boolean = false, var responseType: Status? = null, var data: T?= null, var error: Exception?= null, var event: Unit ? = Unit)

enum class Status {SUCCESSFUL, ERROR, LOADING}
