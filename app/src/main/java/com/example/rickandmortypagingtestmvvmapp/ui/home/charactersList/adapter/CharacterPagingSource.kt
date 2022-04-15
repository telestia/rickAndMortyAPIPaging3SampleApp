package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortypagingtestmvvmapp.remote.characters.RickandMortyApiService
import com.example.rickandmortypagingtestmvvmapp.remote.model.CharactersResult

class CharacterPagingSource(private val apiService:RickandMortyApiService) : PagingSource<Int, CharactersResult>() {

    override fun getRefreshKey(state: PagingState<Int, CharactersResult>): Int? {
        val page = state.closestPageToPosition(state.anchorPosition ?: 1)
        return page?.nextKey?.minus(1) ?: page?.prevKey?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResult> {
        return try {
            val requestedPage = params.key ?: 1
            val response = apiService.getCharacterDataFromAPI(requestedPage)
            val data = response.results

            LoadResult.Page(
                data = data,
                prevKey = if (requestedPage == 1) null else requestedPage - 1,
                nextKey = if (data.isEmpty() || data.size < PAGINATION_LIMIT) null else requestedPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGINATION_LIMIT = 20
    }
}

