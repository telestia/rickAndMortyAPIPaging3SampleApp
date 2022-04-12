package com.example.rickandmortypagingtestmvvmapp.ui.home.characterDetail

import android.app.Application
import androidx.lifecycle.*
import com.example.rickandmortypagingtestmvvmapp.remote.characters.RickAndMortyApiClient
import com.mellon.newssampleharun.common.handleRequestFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(app : Application) : AndroidViewModel(app) {

    private val apiClient = RickAndMortyApiClient.getApiService(app)

    private val _rickAndMortyCharacterDetailPageViewStateLiveData = MutableLiveData<CharacterDetailPageViewState>()



    val rickAndMortyCharacterDetailPageViewStateLiveData : LiveData<CharacterDetailPageViewState> = _rickAndMortyCharacterDetailPageViewStateLiveData

    fun fetchCharacterDataWithId(id : Int){

        //Bu if eger kullanıcı bu ekrana bir daha gelirse birdaha internetten data çekilmesin diye
        // liveData verileri zaten tutuyor
        if (rickAndMortyCharacterDetailPageViewStateLiveData.value?.characterResult == null){

            val requestFlow = handleRequestFlow { apiClient.getCharacterDataFromAPIWithId(id) }
            viewModelScope.launch(Dispatchers.IO){
                requestFlow.collect{
                    withContext(Dispatchers.Main){
                        _rickAndMortyCharacterDetailPageViewStateLiveData.value =
                            CharacterDetailPageViewState(characterResult = it.data, status = it.status)
                    }

                }
            }
        }



    }

}