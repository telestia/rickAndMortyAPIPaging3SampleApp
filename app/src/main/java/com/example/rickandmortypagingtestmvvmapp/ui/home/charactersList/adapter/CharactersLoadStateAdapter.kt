package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortypagingtestmvvmapp.R
import com.example.rickandmortypagingtestmvvmapp.databinding.ItemCharacterRecyclerViewBinding


    class CharactersLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<CharactersLoadStateViewHolder>() {
        override fun onBindViewHolder(holder: CharactersLoadStateViewHolder, loadState: LoadState) {
            holder.bind(loadState)
        }

        override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CharactersLoadStateViewHolder {
            return CharactersLoadStateViewHolder.create(parent, retry)
        }
    }

    class CharactersLoadStateViewHolder(
        private val binding : ItemCharacterRecyclerViewBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.buttonTryAgain.setOnClickListener { retry.invoke()}
        }
        fun bind(loadState : LoadState){
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.errorMessage.isVisible = loadState is LoadState.Error
        }
        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): CharactersLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_character_recycler_view, parent, false)
                val binding = ItemCharacterRecyclerViewBinding.bind(view)
                return CharactersLoadStateViewHolder(binding, retry)
            }
        }
    }

