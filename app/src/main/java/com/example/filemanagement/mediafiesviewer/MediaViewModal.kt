package com.example.filemanagement.mediafiesviewer

import android.app.Application
import android.text.Editable.Factory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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

    private val _state = MutableLiveData(MediaAcitivity.STATE_NONE)

    val state: LiveData<Int>
        get() = _state

    fun setState(state: Int) {
        _state.value = state
    }



}