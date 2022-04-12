package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortypagingtestmvvmapp.ui.base.BaseFragment
import com.example.rickandmortypagingtestmvvmapp.R
import com.example.rickandmortypagingtestmvvmapp.databinding.CharactersListFragmentBinding
import com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter.CharacterPagingAdapter
import com.example.rickandmortypagingtestmvvmapp.ui.home.MainActivityViewModel
import com.mellon.newssampleharun.common.extensions.observeNonNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersListFragment : BaseFragment<CharactersListFragmentBinding>() {

    private val charactersRecyclerViewAdapter : CharacterPagingAdapter by lazy(LazyThreadSafetyMode.NONE){
        CharacterPagingAdapter()
    }// Bunu düz tanımlarsak ne olur neden böyle yapıyoruz cagrıldıgı zaman init ediliyor ama zaten init edilmicek mi her türlü

    private val charactersListViewModel : CharactersListViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()

    override fun getLayoutResId(): Int = R.layout.characters_list_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        handleViewOptions()
        //charactersListViewModel.fetchCharactersData()
        charactersRecyclerViewAdapter.setItemClickListener {
            mainActivityViewModel.onNewsItemClicked(characterResult =  it)
        }

        with(charactersListViewModel){
            charactersListPageViewStateLiveData.observeNonNull(viewLifecycleOwner){
                binding.viewState = it
                it.characters?.let { response ->

                    //charactersRecyclerViewAdapter.setItems(items = response.results)
                }
            }

            pagingDataCharactersListLiveData.observeNonNull(viewLifecycleOwner){
                CoroutineScope(Dispatchers.IO).launch {
                    charactersRecyclerViewAdapter.submitData(it)
                }
            }
        }


    }

    private fun handleViewOptions() {
        charactersRecyclerViewAdapter.setItemClickListener {

        }
    }

    private fun initRecyclerView() {
        with(binding.recyclerViewCharacterList){
            val recyclerViewLayoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            layoutManager = recyclerViewLayoutManager
            adapter = charactersRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        }

    }


}