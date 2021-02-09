package com.ricardoteixeira.movietvshowsexplorer.data.mappers.common

abstract class Mapper<in E, T> {
    abstract fun mapFrom(from: E): T

}