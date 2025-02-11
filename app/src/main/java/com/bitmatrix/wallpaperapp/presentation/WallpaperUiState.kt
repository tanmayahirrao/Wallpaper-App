package com.bitmatrix.wallpaperapp.presentation

import com.bitmatrix.wallpaperapp.domain.entity.WallpaperLink

sealed class WallpaperUiState {
    object loading: WallpaperUiState()
    object EmptyList: WallpaperUiState()

    //update data when there is success
    data class Success(val data: List<WallpaperLink>): WallpaperUiState()

    //dont update when there is error
    data class Error(val message: String): WallpaperUiState()
}