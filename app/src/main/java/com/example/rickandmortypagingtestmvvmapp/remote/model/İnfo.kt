package com.example.rickandmortypagingtestmvvmapp.remote.model

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)