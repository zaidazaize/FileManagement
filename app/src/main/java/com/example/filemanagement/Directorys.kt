package com.example.filemanagement

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.storage.StorageManager
import android.os.storage.StorageManager.ACTION_CLEAR_APP_CACHE
import android.os.storage.StorageManager.ACTION_MANAGE_STORAGE
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import com.example.filemanagement.databinding.ActivityDirectorysBinding
import kotlinx.coroutines.launch
import java.util.UUID

class Directorys : AppCompatActivity() {
    private lateinit var binding: ActivityDirectorysBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDirectorysBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFilesDir.setOnClickListener() {
            lifecycleScope.launch {
                val filesDir = filesDir
                binding.textViewShowFilesDir.text = filesDir.toString()
            }
        }
        binding.buttonIntCacheDir.setOnClickListener() {
            lifecycleScope.launch {
                val cacheDir = cacheDir
                binding.textViewShowFilesDir.text = cacheDir.toString()
            }
        }
        binding.buttonExtFilesDir.setOnClickListener() {
            lifecycleScope.launch {
                val externalFilesDir = getExternalFilesDir(null)
                binding.textViewShowFilesDir.text = externalFilesDir.toString()
            }
        }
        binding.buttonExtCacheDir.setOnClickListener() {
            lifecycleScope.launch {
                val externalCacheDir = externalCacheDir
                binding.textViewShowFilesDir.text = externalCacheDir.toString()
            }
        }

        binding.buttonEmptySpace.setOnClickListener() {
            lifecycleScope.launch {
                val storageManager = applicationContext.getSystemService<StorageManager>()!!
                val appSpecificInternalDirUuid: UUID = storageManager.getUuidForPath(filesDir)
                val freeSpace = storageManager.getAllocatableBytes(appSpecificInternalDirUuid)
                binding.textViewShowFilesDir.text = (freeSpace / (1024 * 1024)).toString()
            }
        }
        binding.buttonManageStorage.setOnClickListener() {
            val manageIntent: Intent = Intent().apply {
                action = ACTION_MANAGE_STORAGE
            }
            startActivity(manageIntent)
        }

        binding.buttonClearCache.setOnClickListener() {
            val intent : Intent = Intent().apply {
                action = ACTION_CLEAR_APP_CACHE
            }
            startActivity(intent)
        }
    }

}
