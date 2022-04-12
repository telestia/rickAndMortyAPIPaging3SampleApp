package com.example.rickandmortypagingtestmvvmapp.remote.characters


import com.example.rickandmortypagingtestmvvmapp.remote.model.CharactersResult
import com.example.rickandmortypagingtestmvvmapp.remote.model.RickAndMortyResponse
import retrofit2.http.GET
import retrofit2.http.Path

import retrofit2.http.Query

interface RickandMortyApiService {


    //curl -H 'Authorization: Bearer API_KEY' https://api.clashroyale.com/v1/players/%238VRJCYJV/upcomingchests

    /*
    @GET("news/{newsId}")
    suspend fun newsDetail(@Path("newsId") newsId: Int): NewsModel

    @Headers("Authorization: Bearer ${Constants.API_KEY}")
    @GET("players/{playerId}/upcomingchests")
    suspend fun getChestPlacement(@Path("playerId") playerId: String): ChestTrackerResponse
     */

    @GET("character")
    suspend fun getCharacterDataFromAPI(@Query("page") query: Int):RickAndMortyResponse

    @GET("character/{characterId}")
    suspend fun getCharacterDataFromAPIWithId(@Path("characterId") characterId : Int) : CharactersResult

}