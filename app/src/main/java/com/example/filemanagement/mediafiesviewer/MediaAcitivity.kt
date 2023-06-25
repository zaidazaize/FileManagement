package com.example.filemanagement.mediafiesviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filemanagement.R
import com.example.filemanagement.databinding.ActivityMediaAcitivityBinding

class MediaAcitivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediaAcitivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}