package com.example.filemanagement.mediafiesviewer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.filemanagement.mediafiesviewer.models.MediaFiles
import com.example.filemanagement.mediafiesviewer.models.MediaImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MediaViewModal(
    private val repository: MediaRepository,
    private val application: Application
) : AndroidViewModel(application) {

    companion object {
        //create a factory to create the view model

        @Suppress("UNCHECKED_CAST")
        fun getFactory(application: Application) : ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = MediaRepository(application)
                return MediaViewModal(repository,application) as T
            }
        }
    }

    private val _state = MutableLiveData(MediaActivity.STATE_NONE)

    val state: LiveData<Int>
        get() = _state

    fun setState(state: Int) {
        _state.value = state
    }

    fun getImages(): LiveData<List<MediaImage>> {
        viewModelScope.launch {
            getImagesFromRepository()
        }
        return repository.images
    }

    private suspend fun getImagesFromRepository(){
        withContext(Dispatchers.IO){
            repository.loadImages()
        }
    }

    // load videos from media store
    fun getMedia(): LiveData<List<MediaFiles>>{
        viewModelScope.launch {
            getMediaFromRepository()
        }
        return repository.mediaFiles
    }
    private suspend fun getMediaFromRepository(){
        withContext(Dispatchers.IO){
            repository.loadMediaFiles()
        }
    }




}