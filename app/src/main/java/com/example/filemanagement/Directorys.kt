package com.example.filemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.filemanagement.databinding.ActivityDirectorysBinding
import kotlinx.coroutines.launch

class Directorys : AppCompatActivity() {
    private  lateinit var  binding : ActivityDirectorysBinding
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

    }
}