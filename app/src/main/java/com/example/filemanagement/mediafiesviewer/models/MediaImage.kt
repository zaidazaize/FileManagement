package com.example.filemanagement.mediafiesviewer.models

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class MediaImage(
    val id: Long,
    val Uri: Uri,
    val name: String,
    val size: String){

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaImage>() {
            override fun areItemsTheSame(oldItem: MediaImage, newItem: MediaImage): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: MediaImage, newItem: MediaImage): Boolean {
                return oldItem == newItem
            }

        }
    }
}
