package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortypagingtestmvvmapp.ui.base.BaseFragment
import com.example.rickandmortypagingtestmvvmapp.R
import com.example.rickandmortypagingtestmvvmapp.databinding.CharactersListFragmentBinding
import com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter.CharacterPagingAdapter
import com.example.rickandmortypagingtestmvvmapp.ui.home.MainActivityViewModel
import com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter.CharactersLoadStateAdapter
import com.mellon.newssampleharun.common.extensions.observeNonNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersListFragment : BaseFragment<CharactersListFragmentBinding>() {

    private val charactersRecyclerViewAdapter : CharacterPagingAdapter by lazy(LazyThreadSafetyMode.NONE){
        CharacterPagingAdapter()
    }

    private val charactersListViewModel : CharactersListViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()

    override fun getLayoutResId(): Int = R.layout.characters_list_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        charactersRecyclerViewAdapter.setItemClickListener {
            mainActivityViewModel.onNewsItemClicked(characterResult =  it)
        }

        with(charactersListViewModel){
            charactersListPageViewStateLiveData.observeNonNull(viewLifecycleOwner){
                with(binding){
                    viewState = it
                    executePendingBindings()
                }
            }
            pagingDataCharactersListLiveData.observeNonNull(viewLifecycleOwner){
                viewModelScope.launch(Dispatchers.Main) {
                    charactersRecyclerViewAdapter.submitData(it)
                }
            }
        }
        binding.recyclerViewCharacterList.adapter = charactersRecyclerViewAdapter.withLoadStateFooter(
            footer = CharactersLoadStateAdapter(){ charactersRecyclerViewAdapter.retry()}
        )
        charactersRecyclerViewAdapter.addLoadStateListener { loadState ->
            // == kullanıca hata veriyor ama is ile çalışıyor neden ?
            when(loadState.refresh){
                is LoadState.Loading -> charactersListViewModel.onLoading()
                is LoadState.Error -> charactersListViewModel.onFindError()
                is LoadState.NotLoading -> charactersListViewModel.isNotLoading()
            }
        }
    }



    private fun initRecyclerView() {
        with(binding.recyclerViewCharacterList){
            val recyclerViewLayoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            layoutManager = recyclerViewLayoutManager
            adapter = charactersRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        }
        binding.buttonTryAgain.setOnClickListener { charactersRecyclerViewAdapter.retry() }
    }


}