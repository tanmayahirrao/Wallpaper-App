package com.bitmatrix.wallpaperapp.presentation.view

import android.app.Activity
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bitmatrix.wallpaperapp.databinding.ActivityMainBinding
import com.bitmatrix.wallpaperapp.domain.entity.WallpaperLink
import com.bitmatrix.wallpaperapp.presentation.WallpaperUiState
import com.bitmatrix.wallpaperapp.presentation.adapter.ImagesRecyclerViewAdapter
import com.bitmatrix.wallpaperapp.presentation.adapter.ItemOnClickListener
import com.bitmatrix.wallpaperapp.presentation.viewmodel.WallpaperViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemOnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var wallpaperAdapter: ImagesRecyclerViewAdapter

    private val wallpaperViewModel: WallpaperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // 1. Setup views
        // 2. collect the state -> loading, success, error
        // 3. update our wallpaper from rest api
        // 4. update the UI

        setupViews()
        collectStateUI()
        wallpaperViewModel.fetchWallpaper()
    }
    private fun setupViews() {
        binding.idImagesRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }

    private fun collectStateUI() {
        lifecycleScope.launch(Dispatchers.Main) {
            wallpaperViewModel.wallpaperList.collect { wallpaperUiState ->
                when(wallpaperUiState) {
                    is WallpaperUiState.loading ->{
                        binding.idProgressBar.isVisible = true
                        Toast.makeText(this@MainActivity, "Wallpapers Loading...", Toast.LENGTH_SHORT).show()
                    }
                    is WallpaperUiState.Success -> {
                        binding.idProgressBar.isVisible = false
                        populateDataInRecyclerView(wallpaperUiState.data)
                    }
                    is WallpaperUiState.EmptyList -> {
                        binding.idProgressBar.isVisible = false
                        Toast.makeText(this@MainActivity, "Wallpapers are Empty", Toast.LENGTH_SHORT).show()
                    }
                    is WallpaperUiState.Error -> {
                        binding.idProgressBar.isVisible = false
                        Toast.makeText(this@MainActivity, "Error 404", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    private fun populateDataInRecyclerView(list: List<WallpaperLink>) {
        // 1. Update WallpaperAdapter with the list
        // 2. Update RecyclerView with that adapter

        wallpaperAdapter = ImagesRecyclerViewAdapter(list, this)
        binding.idImagesRecyclerView.adapter = wallpaperAdapter

    }

    override fun onClickImage(wallpaperLink: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val wallpaperManager = WallpaperManager.getInstance(this@MainActivity)
                    val bitmap: Bitmap = Glide.with(this@MainActivity)
                        .asBitmap()
                        .load(wallpaperLink)
                        .submit()
                        .get() // Get the bitmap from URL
                    wallpaperManager.setBitmap(bitmap)
                    withContext(Dispatchers.Main) {
                        Snackbar.make(binding.root, "Wallpaper Set!", Snackbar.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Snackbar.make(binding.root, "Error setting wallpaper", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}