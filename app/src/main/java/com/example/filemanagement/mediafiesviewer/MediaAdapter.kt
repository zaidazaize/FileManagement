package com.example.filemanagement.mediafiesviewer

import android.provider.MediaStore.Audio.Media
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanagement.databinding.ListItemBinding
import com.example.filemanagement.databinding.ListItemMediaBinding
import com.example.filemanagement.mediafiesviewer.models.MediaImage
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class MediaAdapter :
    ListAdapter<MediaImage, MediaAdapter.MediaViewHolder>(MediaImage.DiffCallback) {
    inner class MediaViewHolder(private val listItemBinding: ListItemMediaBinding) : RecyclerView.ViewHolder(listItemBinding.root) {
        fun bind(mediaImage: MediaImage) {
            listItemBinding.apply {
                nameTextView.text = mediaImage.name
                sizeTextView.text = mediaImage.size.toString()
                imageViewShowImage.setImageURI(mediaImage.uri)
            }
        }
    }

    public val state: Int = MediaAcitivity.STATE_NONE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ListItemMediaBinding.inflate(LayoutInflater.from(parent.context))
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}