package com.example.rickandmortypagingtestmvvmapp.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.example.rickandmortypagingtestmvvmapp.ui.base.BaseActivity
import com.example.rickandmortypagingtestmvvmapp.R
import com.example.rickandmortypagingtestmvvmapp.databinding.ActivityMainBinding
import com.mellon.newssampleharun.common.extensions.observeNonNull

class MainActivity : BaseActivity<ActivityMainBinding>()  {

    private val navController by lazy(LazyThreadSafetyMode.NONE){
        findNavController(R.id.fragmentContainerView)
    }
    private val mainActivityViewModel : MainActivityViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityViewModel.navigateDestinationSingleLiveEvent.observeNonNull(this){
            navigateToDestination(navController = navController, navigationData =  it)
        }
    }
}