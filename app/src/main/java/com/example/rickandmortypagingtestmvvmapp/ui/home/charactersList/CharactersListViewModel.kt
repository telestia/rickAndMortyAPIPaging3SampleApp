package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.example.rickandmortypagingtestmvvmapp.remote.Status
import com.example.rickandmortypagingtestmvvmapp.remote.characters.RickAndMortyApiClient
import com.example.rickandmortypagingtestmvvmapp.remote.model.CharactersResult
import com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter.CharacterPagingSource
import kotlinx.coroutines.launch

class CharactersListViewModel(app :Application) : AndroidViewModel(app) {

    private val charactersApiClient = RickAndMortyApiClient.getApiService(app)

    private val _charactersListPageViewStateLiveData = MutableLiveData<CharactersListPageViewState>()
    private val _pagingDataCharactersListLiveData = MutableLiveData<PagingData<CharactersResult>>()

    val pagingDataCharactersListLiveData : LiveData<PagingData<CharactersResult>> = _pagingDataCharactersListLiveData
    val  charactersListPageViewStateLiveData : LiveData<CharactersListPageViewState> = _charactersListPageViewStateLiveData

    val charactersListPagingFlow = Pager(PagingConfig(pageSize = 20, prefetchDistance = 2)){
        CharacterPagingSource(RickAndMortyApiClient,app)
    }.flow.cachedIn(viewModelScope)
    init {
        _charactersListPageViewStateLiveData.value = CharactersListPageViewState()
        pagingAdapter()
    }

    fun pagingAdapter(){
        viewModelScope.launch{
            charactersListPagingFlow.collect{
                _pagingDataCharactersListLiveData.value = it
            }
        }
    }
    fun onFindError(){
        _charactersListPageViewStateLiveData.value = CharactersListPageViewState(Status.ERROR)
    }
    fun onLoading(){
        _charactersListPageViewStateLiveData.value = CharactersListPageViewState(Status.LOADING)
    }
    fun isNotLoading(){
        _charactersListPageViewStateLiveData.value = CharactersListPageViewState(Status.SUCCESS)
    }

}