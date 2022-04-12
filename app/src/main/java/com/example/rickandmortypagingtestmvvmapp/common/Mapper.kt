package com.mellon.newssampleharun.common

interface Mapper<T, R> {
    fun mapTo(from : T): R
}