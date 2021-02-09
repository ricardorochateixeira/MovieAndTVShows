package com.ricardoteixeira.movietvshowsexplorer.domain.usecases.common

interface BaseUseCase<in Parameter, out Result> {
    suspend operator fun invoke(params: Parameter): Result
}