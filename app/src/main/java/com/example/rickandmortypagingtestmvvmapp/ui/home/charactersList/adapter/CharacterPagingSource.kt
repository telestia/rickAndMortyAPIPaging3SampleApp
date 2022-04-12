package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter

import android.content.Context
import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortypagingtestmvvmapp.remote.characters.RickAndMortyApiClient
import com.example.rickandmortypagingtestmvvmapp.remote.model.CharactersResult


class CharacterPagingSource(private val api : RickAndMortyApiClient,private val context: Context) : PagingSource<Int,CharactersResult>() {
    override fun getRefreshKey(state: PagingState<Int, CharactersResult>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResult> {
        val currentPageNumber  = params.key ?: 1

        try {
            val response = api.getApiService(context = context).getCharacterDataFromAPI(currentPageNumber)
            val data = response.results

            var nextPageNumber : Int? = null

            val uri = Uri.parse(response.info.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery?.toInt()

            return LoadResult.Page(
                data = data,
                prevKey = null,//Bizim prevKey ne?
                nextKey = nextPageNumber
            )
        }catch (e : Exception){
            return LoadResult.Error(e)
        }
    }


}

