package com.example.filemanagement.mediafiesviewer.models

import android.net.Uri

data class MediaFiles(
    val id: Long,
    val uri: Uri,
    val name: String,
    val MimeType: String,
    val size : Int
) {
    companion object {
        val DiffCallback = object : androidx.recyclerview.widget.DiffUtil.ItemCallback<MediaFiles>() {
            override fun areItemsTheSame(oldItem: MediaFiles, newItem: MediaFiles): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MediaFiles, newItem: MediaFiles): Boolean {
                return oldItem == newItem
            }

        }
    }
}
