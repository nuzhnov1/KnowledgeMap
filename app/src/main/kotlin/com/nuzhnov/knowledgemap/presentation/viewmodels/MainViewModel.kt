package com.nuzhnov.knowledgemap.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.nuzhnov.knowledgemap.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val roadmap get() = repository.roadmap
}
