package com.ricardoteixeira.movietvshowsexplorer.domain.state

data class DataState<T>(
    val stateMessage: StateMessage? = null,
    var data: T? = null
) {
    companion object {

        fun <T> error(response: Response): DataState<T> {
            return DataState(
                stateMessage = StateMessage(response),
                data = null
            )
        }

        fun <T> data(response: Response?, data: T? = null): DataState<T> {
            return DataState(
                stateMessage = response?.let { StateMessage(it) },
                data = data
            )
        }
    }
}

data class StateMessage(val response: Response)

data class Response(val message: String?)