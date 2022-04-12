package com.example.rickandmortypagingtestmvvmapp.remote.model

data class RickAndMortyResponse(
    val info: Info,
    val results: List<CharactersResult>
)