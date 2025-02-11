package com.bitmatrix.wallpaperapp.data.api

import com.bitmatrix.wallpaperapp.Utils.Constants.BASE_URL
import com.bitmatrix.wallpaperapp.data.api.model.PicSumItem
import retrofit2.http.GET
import retrofit2.http.Query


interface PicSumApi {
    @GET(BASE_URL) //URL
    suspend fun getWallpaperImages(
        @Query("page") page: Int, @Query("limit") limit: Int = 100
    ): List<PicSumItem>? //ApiPicSumItem is Data Class
}