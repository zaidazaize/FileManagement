package com.example.filemanagement.mediafiesviewer

import android.app.Application
import android.content.ContentUris
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.filemanagement.mediafiesviewer.models.MediaFiles
import com.example.filemanagement.mediafiesviewer.models.MediaImage
import com.example.filemanagement.mediafiesviewer.models.MediaVideo

class MediaRepository(private val application: Application) {

    // live data for images
    private val _images = MutableLiveData<List<MediaImage>>()
    val images: LiveData<List<MediaImage>>
        get() = _images

    private val _videos = MutableLiveData<List<MediaVideo>>()
    val videos: LiveData<List<MediaVideo>>
        get() = _videos

    private val _mediaFiles = MutableLiveData<List<MediaFiles>>()
    val mediaFiles: LiveData<List<MediaFiles>>
        get() = _mediaFiles

    // load images from media store
    fun loadImages() {
        //collections name of images
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        application.contentResolver.query(
            collection,
            arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
            ),
            null,
            null,
            "${MediaStore.Images.Media.DATE_MODIFIED} ASC"
        ).use { cursor ->
            if (cursor != null) {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val images = mutableListOf<MediaImage>()
                Log.d("MediaRepository", "loadImages: ${cursor.count}")
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val size = cursor.getString(sizeColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    val item = MediaImage(id, contentUri, name, size)
                    images.add(item)
                }
                _images.postValue(images)
            }
        }
    }

    // load videos from media store
    fun loadVideos() {
        //collections name of videos
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }
        application.contentResolver.query(
            collection,
            arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION
            ),
            null,
            null,
            "${MediaStore.Video.Media.DATE_MODIFIED} ASC"
        ).use { cursor ->
            if (cursor != null) {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val videos = mutableListOf<MediaVideo>()
                Log.d("MediaRepository", "loadVideos: ${cursor.count}")
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val size = cursor.getString(sizeColumn)
                    val duration = cursor.getString(durationColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    val item = MediaVideo(contentUri, id, name, size, duration)
                    videos.add(item)
                }
                _videos.postValue(videos)
            }
        }
    }

    //load media files from media store
    fun loadMediaFiles() {
        //collections name of media files
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Files.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
            else MediaStore.Files.getContentUri("external")

        //query the media files
        application.contentResolver.query(
            collection,
            arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.MediaColumns.SIZE
            ),
            null, null, "${MediaStore.MediaColumns.DATE_MODIFIED} ASC"
        ).use { cursor ->
            if (cursor != null) {
                val idIndex = cursor.getColumnIndex(MediaStore.MediaColumns._ID)
                val nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
                val typeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)
                val sizeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.SIZE)

                val list = mutableListOf<MediaFiles>()
                if (!cursor.isAfterLast)
                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idIndex)
                        val name = cursor.getString(nameIndex)
                        val type = cursor.getString(typeIndex)
                        val size = cursor.getInt(sizeIndex)
                        val contentUri = ContentUris.withAppendedId(collection, id)
                        list.add(MediaFiles(id, contentUri, name, type, size))
                    }

                _mediaFiles.postValue(list)
            }
        }
    }
}