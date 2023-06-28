package com.example.filemanagement.mediafiesviewer

import android.content.ContentResolver.MimeTypeInfo
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filemanagement.databinding.ListItemMediaBinding
import com.example.filemanagement.mediafiesviewer.models.MediaFiles
import com.example.filemanagement.mediafiesviewer.models.MediaImage

class MediaAdapter :
    ListAdapter<MediaFiles, MediaAdapter.MediaViewHolder>(MediaFiles.DiffCallback) {
    companion object{
          const val MB = 1024 * 1024
      }
    inner class MediaViewHolder(private val listItemBinding: ListItemMediaBinding) : RecyclerView.ViewHolder(listItemBinding.root) {

        fun bind(mediaFiles: MediaFiles) {
            listItemBinding.apply {
                if(mediaFiles.MimeType == "image/jpeg")
                    chipVideoIndicator.visibility = ViewGroup.GONE
                else
                    chipVideoIndicator.visibility = ViewGroup.VISIBLE
                nameTextView.text = mediaFiles.name
                sizeTextView.text = "Size: ${mediaFiles.size / 1024} KB"

                // load image and video thumbnails
                Glide.with(imageViewShowThumbnail)
                    .load(mediaFiles.uri)
                    .centerCrop()
                    .into(imageViewShowThumbnail)
            }
        }
    }

    public val state: Int = MediaActivity.STATE_NONE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ListItemMediaBinding.inflate(LayoutInflater.from(parent.context))
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}