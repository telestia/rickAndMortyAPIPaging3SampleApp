package com.example.rickandmortypagingtestmvvmapp.ui.home.characterDetail

import android.view.View
import com.example.rickandmortypagingtestmvvmapp.remote.Status
import com.example.rickandmortypagingtestmvvmapp.remote.model.CharactersResult

data class CharacterDetailPageViewState(
    val characterResult : CharactersResult?,
    val status : Status = Status.SUCCESS
){

    fun isContentVisible() = if (status == Status.SUCCESS) View.VISIBLE else View.GONE

    fun isLoadingVisible() = if (status == Status.LOADING) View.VISIBLE else View.GONE

    fun isErrorVisible() = if (status == Status.ERROR) View.VISIBLE else View.GONE
}


