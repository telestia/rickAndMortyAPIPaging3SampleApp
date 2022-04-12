package com.example.rickandmortypagingtestmvvmapp.remote.characters

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.bookpricesmvvmtestapp.common.util.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RickAndMortyApiClient {

    private var rickAndMortyApiSystem: RickandMortyApiService? = null

    fun getApiService(context: Context): RickandMortyApiService {

        rickAndMortyApiSystem = getRetrofit(context).create(RickandMortyApiService::class.java)

        return rickAndMortyApiSystem!!
    }

    private fun getRetrofit(context: Context): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        val builder = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        val client = builder.build()

        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}