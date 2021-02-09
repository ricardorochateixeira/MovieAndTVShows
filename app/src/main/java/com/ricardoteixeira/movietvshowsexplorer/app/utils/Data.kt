package com.ricardoteixeira.movietvshowsexplorer.app.utils

import kotlinx.coroutines.channels.Channel

sealed class Data<T>(var responseType: Status, var data: T?= null, var error: Exception?= null, event: Channel<T>)

enum class Status {SUCCESSFUL, ERROR, LOADING}
