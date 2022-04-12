package com.example.rickandmortypagingtestmvvmapp.ui.home

import ARG_CHARACTER_DETAILS
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortypagingtestmvvmapp.R
import com.example.rickandmortypagingtestmvvmapp.remote.model.CharactersResult
import com.example.rickandmortypagingtestmvvmapp.ui.home.characterDetail.CharacterDetailArguments
import com.mellon.newssampleharun.common.SingleLiveEvent
import com.mellon.newssampleharun.ui.common.navigation.NavigationData

class MainActivityViewModel  : ViewModel() {

    private val _navigateDestinationSingleLiveEvent : SingleLiveEvent<NavigationData> = SingleLiveEvent()
    private val _navigateUpSingleLiveEvent : SingleLiveEvent<Any?>  = SingleLiveEvent()



    val navigateDestinationSingleLiveEvent : LiveData<NavigationData> = _navigateDestinationSingleLiveEvent
    val navigateUpSingleLiveEvent : LiveData<Any?> = _navigateUpSingleLiveEvent

    fun onNewsItemClicked(characterResult: CharactersResult) {
        _navigateDestinationSingleLiveEvent.value = NavigationData(
            destinationId =R.id.characterDetailFragment,
            args = bundleOf(
                ARG_CHARACTER_DETAILS to CharacterDetailArguments(
                    characterId = characterResult.id
                )
            )
        )
    }
}