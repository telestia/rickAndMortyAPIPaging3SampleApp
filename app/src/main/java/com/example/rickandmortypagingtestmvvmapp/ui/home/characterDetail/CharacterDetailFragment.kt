package com.example.rickandmortypagingtestmvvmapp.ui.home.characterDetail

import ARG_CHARACTER_DETAILS
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.rickandmortypagingtestmvvmapp.R
import com.example.rickandmortypagingtestmvvmapp.databinding.FragmentCharacterDetailBinding
import com.example.rickandmortypagingtestmvvmapp.ui.base.BaseFragment
import com.mellon.newssampleharun.common.extensions.observeNonNull

class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding>() {
    override fun getLayoutResId(): Int = R.layout.fragment_character_detail

    private val charactersDetailArguments : CharacterDetailArguments by lazy(LazyThreadSafetyMode.NONE){
        arguments?.getParcelable(ARG_CHARACTER_DETAILS)!!
    }

    private val charactersDetailViewModel : CharacterDetailViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleViewOptions()

        charactersDetailViewModel.fetchCharacterDataWithId(charactersDetailArguments.characterId)

        charactersDetailViewModel.rickAndMortyCharacterDetailPageViewStateLiveData.observeNonNull(viewLifecycleOwner){
            with(binding){
                println(it.characterResult)
                itemViewState = it
                executePendingBindings()
            }
        }
    }

    private fun handleViewOptions() {
        binding.buttonTryAgain.setOnClickListener { charactersDetailViewModel.fetchCharacterDataWithId(charactersDetailArguments.characterId) }
    }


}