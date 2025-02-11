package com.bitmatrix.wallpaperapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bitmatrix.wallpaperapp.R
import com.bitmatrix.wallpaperapp.domain.entity.WallpaperLink
import com.bumptech.glide.Glide

class ImagesRecyclerViewAdapter(private var dataSet: List<WallpaperLink>, private val itemOnClickListener: ItemOnClickListener) :
    RecyclerView.Adapter<ImagesRecyclerViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: AppCompatImageView

        init {
            // Define click listener for the ViewHolder's View
            imageView = view.findViewById(R.id.image_view)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        //update the imageView with GLide
        Glide.with(viewHolder.imageView.context)
            .load(dataSet[position].wallpaperLink)
            .into(viewHolder.imageView)

        viewHolder.imageView.setOnClickListener {
            itemOnClickListener.onClickImage(dataSet[position].wallpaperLink)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}

interface ItemOnClickListener {
    fun onClickImage(wallpaperLink: String)
}