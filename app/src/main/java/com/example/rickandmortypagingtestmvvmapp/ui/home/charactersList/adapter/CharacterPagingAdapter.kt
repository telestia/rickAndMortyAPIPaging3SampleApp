package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortypagingtestmvvmapp.R
import com.example.rickandmortypagingtestmvvmapp.databinding.ItemCharacterRecyclerViewBinding
import com.example.rickandmortypagingtestmvvmapp.remote.model.CharactersResult

class CharacterPagingAdapter: PagingDataAdapter<CharactersResult, CharacterPagingAdapter.ViewHolder>(DataDiff) {

    private var itemClickListener: ((CharactersResult) -> Unit)? = null

    fun setItemClickListener(listener: (CharactersResult) -> Unit) {
        itemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {holder.bind(it)}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater : ItemCharacterRecyclerViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_character_recycler_view,parent,false)
        return ViewHolder(inflater)
    }


    inner class ViewHolder(val binding : ItemCharacterRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let { it1 ->
                    itemClickListener?.invoke(it1) }
            }
        }
        fun bind(charactersResult: CharactersResult) {
            with(binding){
                character = charactersResult
                executePendingBindings()
            }
        }
    }

    object DataDiff : DiffUtil.ItemCallback<CharactersResult>(){
        override fun areItemsTheSame(oldItem: CharactersResult, newItem: CharactersResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharactersResult, newItem: CharactersResult): Boolean {
            return oldItem == newItem
        }

    }

}


