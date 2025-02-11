package com.bitmatrix.wallpaperapp.domain.repository

import com.bitmatrix.wallpaperapp.Utils.Resource
import com.bitmatrix.wallpaperapp.domain.entity.WallpaperLink
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    fun getImages(): Flow<Resource<List<WallpaperLink>>>
}