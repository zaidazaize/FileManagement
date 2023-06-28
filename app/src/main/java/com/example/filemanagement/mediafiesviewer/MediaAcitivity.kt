package com.example.filemanagement.mediafiesviewer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanagement.R
import com.example.filemanagement.databinding.ActivityMediaAcitivityBinding
import com.google.android.material.snackbar.Snackbar

class MediaAcitivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMediaAcitivityBinding
    private lateinit var mediaViewModal: MediaViewModal
    private lateinit var mediaAdapter: MediaAdapter
    private val permissionStatus = MutableLiveData(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaAcitivityBinding.inflate(layoutInflater)
        binding.buttonShowImages.setOnClickListener(this)

        mediaViewModal = ViewModelProvider(
            this,
            MediaViewModal.getFactory(application)
        )[MediaViewModal::class.java]

        // handle recycle view
        mediaAdapter = MediaAdapter()
        binding.recyclerViewShowMedia.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewShowMedia.adapter = mediaAdapter

        //observe state changes and update recycle view
        mediaViewModal.state.observe(this@MediaAcitivity) {
            updateRecycleView(it)
        }

        setContentView(binding.root)

        //check if all permissions are granted
        if (!allPermissionsGranted()) {
            requestPermission()
        }
        mediaViewModal.getImages().observe(this@MediaAcitivity) {
            mediaAdapter.submitList(it)
        }

//        observe permission status and request permission if not granted
//        permissionStatus.observe(this@MediaAcitivity) {
//            if (!it) {
//                requestPermission(true)
//            } else {
//                mediaViewModal.setState(mediaViewModal.state.value!!)
//            }
//
//        }

    }

    private fun updateRecycleView(it: Int) {

//        when(it){
//            STATE_IMAGE -> {
//                mediaAdapter.submitList()
//            }
//            STATE_VIDEO -> {
//                mediaAdapter.submitList(mediaViewModal.videos)
//            }
//            else -> {
//                mediaAdapter.submitList(null)
//            }
//        }

    }

    //check if all permissions are granted
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    //request permissions
    private fun requestPermission(bool: Boolean = false) {
        permissionLauncher().apply {
            if (bool) {
                unregister()
            }
            launch(REQUIRED_PERMISSIONS)

        }
    }

    private fun permissionLauncher() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var isAll = true;

            permissions.entries.forEach() {
                if (!it.value) {
                    isAll = false
                }
            }

            if (isAll) {
                updateRecycleView(mediaViewModal.state.value!!)
            } else {
                Snackbar.make(binding.root, "Permission required", Snackbar.LENGTH_SHORT)
                    .setAction("Grant") {
                        requestPermission()
                    }.setActionTextColor(ContextCompat.getColor(this, R.color.black)).show()
            }
        }

    //TODO : handle permission callback and permission request when user clicks on button
    override fun onClick(v: View?) {
        if (!allPermissionsGranted())
            permissionStatus.value = false;
        else
            when (v?.id) {
                R.id.button_show_images -> mediaViewModal.setState(STATE_IMAGE)
            }
    }

    companion object {
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                plus(Manifest.permission.ACCESS_MEDIA_LOCATION)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                plus(Manifest.permission.READ_MEDIA_IMAGES)
                plus(Manifest.permission.READ_MEDIA_VIDEO)
                plus(Manifest.permission.READ_MEDIA_AUDIO)
            }

        }
        const val STATE_NONE = 0
        const val STATE_IMAGE = 1
        const val STATE_VIDEO = 2
    }

}