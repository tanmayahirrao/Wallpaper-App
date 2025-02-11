package com.bitmatrix.wallpaperapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitmatrix.wallpaperapp.Utils.Resource
import com.bitmatrix.wallpaperapp.domain.repository.WallpaperRepository
import com.bitmatrix.wallpaperapp.presentation.WallpaperUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(private val repository: WallpaperRepository): ViewModel() {

    private val _wallpaperList: MutableStateFlow<WallpaperUiState> =
        MutableStateFlow(WallpaperUiState.loading)

    val wallpaperList get() = _wallpaperList.asStateFlow()

    fun fetchWallpaper() {
        //coroutines
        viewModelScope.launch(Dispatchers.IO) {
            repository.getImages().collect { resource ->
                when(resource) {
                    is Resource.Error -> {
                        _wallpaperList.update { WallpaperUiState.Error(resource.message.orEmpty()) }
                    }
                    is Resource.Success -> {
                        if(resource.data.isNullOrEmpty().not()) {
                            _wallpaperList.update { WallpaperUiState.Success(resource.data!!) }
                        }
                        else {
                            _wallpaperList.update { WallpaperUiState.EmptyList }
                        }
                    }
                }

            }
        }
    }
}