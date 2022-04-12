package com.mellon.newssampleharun.common.extensions

fun LongArray?.orEmpty(): LongArray {
    return this ?: longArrayOf()
}