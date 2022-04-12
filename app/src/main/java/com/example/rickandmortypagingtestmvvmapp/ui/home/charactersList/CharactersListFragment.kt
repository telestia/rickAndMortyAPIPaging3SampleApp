package com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortypagingtestmvvmapp.R
import com.example.rickandmortypagingtestmvvmapp.databinding.FragmentCharactersListBinding
import com.example.rickandmortypagingtestmvvmapp.ui.base.BaseFragment
import com.example.rickandmortypagingtestmvvmapp.ui.home.MainActivityViewModel
import com.example.rickandmortypagingtestmvvmapp.ui.home.charactersList.adapter.CharacterPagingAdapter
import com.mellon.newssampleharun.common.extensions.observeNonNull
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class CharactersListFragment : BaseFragment<FragmentCharactersListBinding>() {

    private val charactersRecyclerViewAdapter: CharacterPagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterPagingAdapter()
    }// Bunu düz tanımlarsak ne olur neden böyle yapıyoruz cagrıldıgı zaman init ediliyor ama zaten init edilmicek mi her türlü

    private val charactersListViewModel: CharactersListViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_characters_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersRecyclerView()
        charactersRecyclerViewAdapter.setItemClickListener {
            mainActivityViewModel.onNewsItemClicked(characterResult = it)
        }

        with(charactersListViewModel) {
            charactersListPageViewStateLiveData.observeNonNull(viewLifecycleOwner) {
                with(binding) {
                    viewState = it
                    executePendingBindings()
                }
            }
        }
    }

    private fun initCharactersRecyclerView() {
        with(binding.recyclerViewCharacterList) {
            val recyclerViewLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            layoutManager = recyclerViewLayoutManager
            adapter = charactersRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
        charactersRecyclerViewAdapter.setItemClickListener { }
        lifecycleScope.launch {
            charactersListViewModel.charactersListPagingFlow.distinctUntilChanged().collectLatest {
                charactersRecyclerViewAdapter.submitData(it)
            }
        }
    }
}