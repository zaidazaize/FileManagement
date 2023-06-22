package com.example.filemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanagement.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import java.io.File

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModal: MainViewModal
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModal = MainViewModal()
        binding.writeToFile.setOnClickListener {
            writeToInternalStorage()
        }
        binding.outputStreamWriter.setOnClickListener {
            writeToInternalStorageUsingOutputStream()
        }
        binding.viewFilelist.setOnClickListener {
            viewFileList()
        }

        adapter = Adapter()
        binding.fileList.adapter = adapter
        binding.fileList.layoutManager = LinearLayoutManager(binding.root.context)
    }

    //show file list
    private fun viewFileList() {
        lifecycleScope.launch {
            val files = fileList()
            adapter.dataset = files.toMutableList()
        }
    }

    // Write to internal storage using file class
    private fun writeToInternalStorage() {

        lifecycleScope.launch {
            for (i in 0..100) {
                var file = File(filesDir, "myFile${i}.txt")
                var text = "Hello World ${i}"
                file.writeText("Hello World")
            }
        }
    }

    //write to internal storage using output stream\
    private fun writeToInternalStorageUsingOutputStream() {
        lifecycleScope.launch {
            for (i in 0..100) {
                val file = File(filesDir, "myFile${i}.txt")
                val text = "Hello World two ${i}"
                baseContext.openFileOutput(file.name, MODE_APPEND).use {
                    it.write(text.toByteArray())
                }

            }
        }
    }
}