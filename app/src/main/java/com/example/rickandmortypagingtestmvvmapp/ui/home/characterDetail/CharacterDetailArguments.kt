package com.example.rickandmortypagingtestmvvmapp.ui.home.characterDetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDetailArguments(
    val characterId : Int
) : Parcelable
