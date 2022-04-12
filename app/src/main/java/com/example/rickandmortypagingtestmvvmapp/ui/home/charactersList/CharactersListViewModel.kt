package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortypagingtestmvvmapp.remote.characters.RickAndMortyApiClient
import com.example.rickandmortypagingtestmvvmapp.remote.model.CharactersResult
import com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter.CharacterPagingSource
import com.mellon.newssampleharun.common.handleRequestFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersListViewModel(app: Application) : AndroidViewModel(app) {

    private val apiService = RickAndMortyApiClient.getApiService(app)

    private val _charactersListPageViewStateLiveData = MutableLiveData<CharactersListPageViewState>()

    val charactersListPageViewStateLiveData: LiveData<CharactersListPageViewState> = _charactersListPageViewStateLiveData

    val charactersListPagingFlow = Pager(PagingConfig(pageSize = 20)) { CharacterPagingSource(apiService = apiService) }.flow.cachedIn(viewModelScope)

    init {
        _charactersListPageViewStateLiveData.value = CharactersListPageViewState()
    }
}