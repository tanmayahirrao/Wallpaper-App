package com.bitmatrix.wallpaperapp.data.api

import com.bitmatrix.wallpaperapp.Utils.Resource
import com.bitmatrix.wallpaperapp.domain.entity.WallpaperLink
import com.bitmatrix.wallpaperapp.domain.repository.WallpaperRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(private val picSumApi: PicSumApi): WallpaperRepository {
    override fun getImages(): Flow<Resource<List<WallpaperLink>>> = flow{

        try {
            val response = picSumApi.getWallpaperImages(page = 1)

            response?.let {
                val wallpaperLinks: List<WallpaperLink> = response.map {
                        WallpaperLink(it.downloadUrl.orEmpty())
                }
                emit(Resource.Success(wallpaperLinks))
            }
        }
        catch (e: Exception) {
            emit(Resource.Error(null, e.message))
        }
    }
}