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

class CharactersListViewModel(app :Application) : AndroidViewModel(app) {

    private val charactersApiClient = RickAndMortyApiClient.getApiService(app)

    private val _charactersListPageViewStateLiveData = MutableLiveData<CharactersListPageViewState>()
    private val _pagingDataCharactersListLiveData = MutableLiveData<PagingData<CharactersResult>>()

    val pagingDataCharactersListLiveData : LiveData<PagingData<CharactersResult>> = _pagingDataCharactersListLiveData
    val  charactersListPageViewStateLiveData : LiveData<CharactersListPageViewState> = _charactersListPageViewStateLiveData

    val charactersList = Pager(PagingConfig(pageSize = 20, prefetchDistance = 2 )){//prefetchDistance ne demek
        CharacterPagingSource(RickAndMortyApiClient,app)
    }.flow.cachedIn(viewModelScope)
    init {
        _charactersListPageViewStateLiveData.value = CharactersListPageViewState()
        //fetchCharactersData()
        pagingAdapter()
    }
    fun fetchCharactersData(){
        viewModelScope.launch(Dispatchers.IO){

            val requestFlow = handleRequestFlow { charactersApiClient.getCharacterDataFromAPI(1) }
            requestFlow.collect{
                withContext(Dispatchers.Main){

                    _charactersListPageViewStateLiveData.value = CharactersListPageViewState(characters = it.data, status = it.status)
                }

            }
        }
    }

    fun pagingAdapter(){
        viewModelScope.launch(Dispatchers.IO){
            charactersList.collect{
                withContext(Dispatchers.Main){
                    _pagingDataCharactersListLiveData.value = it
                }
            }
        }

    }
}