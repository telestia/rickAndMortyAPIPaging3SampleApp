package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList

import android.view.View
import com.example.rickandmortypagingtestmvvmapp.remote.model.RickAndMortyResponse
import com.example.rickandmortypagingtestmvvmapp.remote.Status

data class CharactersListPageViewState(
    val characters : RickAndMortyResponse? = null,
    val status : Status = Status.SUCCESS
){
  fun isLoadingVisible() = if (status == Status.LOADING) View.VISIBLE else View.GONE

  fun isContentVisible() = if (status == Status.SUCCESS) View.VISIBLE else View.GONE

  fun isErrorVisible() = if (status == Status.ERROR) View.VISIBLE else View.GONE

}

