package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortypagingtestmvvmapp.R
import com.example.rickandmortypagingtestmvvmapp.databinding.ItemCharacterBinding
import com.example.rickandmortypagingtestmvvmapp.remote.model.CharactersResult

class CharacterPagingAdapter : PagingDataAdapter<CharactersResult, CharacterPagingAdapter.ViewHolder>(CharacterDiffUtilItemCallback()) {

    private var itemClickListener: ((CharactersResult) -> Unit)? = null

    fun setItemClickListener(listener: (CharactersResult) -> Unit) {
        itemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: ItemCharacterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_character, parent, false)
        return ViewHolder(inflater)
    }

    inner class ViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let { it1 ->
                    itemClickListener?.invoke(it1)
                }
            }
        }

        fun bind(charactersResult: CharactersResult) {
            with(binding) {
                character = charactersResult
                executePendingBindings()
            }
        }
    }

    class CharacterDiffUtilItemCallback : DiffUtil.ItemCallback<CharactersResult>() {
        override fun areItemsTheSame(oldItem: CharactersResult, newItem: CharactersResult) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CharactersResult, newItem: CharactersResult) = oldItem == newItem
    }
}