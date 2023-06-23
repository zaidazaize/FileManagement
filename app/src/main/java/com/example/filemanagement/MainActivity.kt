package com.example.filemanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanagement.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        const val LAYOUT_STATE_ONE = 0
        const val LAYOUT_STATE_TWO = 1
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModal: MainViewModal
    private lateinit var adapter: Adapter
    private var files: Array<String>? = null

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
        binding.readAllFileNames.setOnClickListener {
            showAllFileContent()
        }


        adapter = Adapter()
        binding.recycleViewFileList.adapter = adapter
        binding.recycleViewFileList.layoutManager = LinearLayoutManager(binding.root.context)
    }

    private fun showAllFileContent() {
        changeVisibility(LAYOUT_STATE_TWO)
        writeAllFileContentToTextView()
    }

    //writes all files content to text view
    private fun writeAllFileContentToTextView() {
        lifecycleScope.launch {
            val files = fileList()
            val text = StringBuilder()
            for (file in files) {
                openFileInput(file).buffered().reader().useLines { lines ->
                    lines.forEach {
                        text.append("\n$it")
                    }
                }
            }
            binding.textViewShowFileContent.text = text.toString()
        }
    }

    //change visibility of recycler view and text view
    private fun changeVisibility(int: Int) {
        if (int == 0) {
            binding.recycleViewFileList.visibility = View.VISIBLE
            binding.linearLayoutShowFileContent.visibility = View.GONE
        } else {
            binding.recycleViewFileList.visibility = View.GONE
            binding.linearLayoutShowFileContent.visibility = View.VISIBLE
        }
    }

    //read all files
    private fun readAllFiles() {
        changeVisibility(LAYOUT_STATE_TWO)
        lifecycleScope.launch {
            var files = fileList()
            for (file in files) {
                var text = File(filesDir, file).readText()
                Log.d("TAG", "readAllFiles: $text")
            }
        }
    }

    //show file list
    private fun viewFileList() {
        changeVisibility(LAYOUT_STATE_ONE)
        lifecycleScope.launch {
            files = fileList()
            adapter.dataset = files?.toMutableList()!!
        }
    }

    // Write to internal storage using file class
    private fun writeToInternalStorage() {

        lifecycleScope.launch {
            for (i in 0..100) {
                val file = File(filesDir, "myFile${i}.txt")
                var text = "Hello World ${i}"
                file.writeText("Hello World")
            }
            writeAllFileContentToTextView()
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
            writeAllFileContentToTextView()
        }
    }
}