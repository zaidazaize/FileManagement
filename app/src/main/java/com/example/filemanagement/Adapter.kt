package com.example.filemanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.newFixedThreadPoolContext
import java.lang.RuntimeException

class Adapter : RecyclerView.Adapter<Adapter.CustomViewHolder>() {
   //make setter public

   var dataset : MutableList<String> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }
    class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }


    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.textView.text = dataset[position]
    }
}