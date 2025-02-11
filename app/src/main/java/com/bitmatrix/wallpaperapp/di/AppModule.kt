package com.bitmatrix.wallpaperapp.di

import com.bitmatrix.wallpaperapp.Utils.Constants.BASE_URL
import com.bitmatrix.wallpaperapp.data.api.PicSumApi
import com.bitmatrix.wallpaperapp.data.api.WallpaperRepositoryImpl
import com.bitmatrix.wallpaperapp.domain.repository.WallpaperRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //can't use @Binds in ActivityComponent rather use SingletonComponent
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideRetrofitApi(
            // Potential dependencies of this type
        ): PicSumApi {
            return Retrofit.Builder()
            .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PicSumApi::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun provideWallpaperRepository(repository: WallpaperRepositoryImpl): WallpaperRepository
}