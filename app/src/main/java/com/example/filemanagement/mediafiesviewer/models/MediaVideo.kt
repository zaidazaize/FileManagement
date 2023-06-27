package com.example.filemanagement.mediafiesviewer.models

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class MediaVideo(
    val uri: Uri,
    val id: Long,
    val name: String,
    val size: String,
    val duration: String
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaVideo>() {
            override fun areItemsTheSame(oldItem: MediaVideo, newItem: MediaVideo): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: MediaVideo, newItem: MediaVideo): Boolean {
                return oldItem == newItem
            }

        }
    }
}
