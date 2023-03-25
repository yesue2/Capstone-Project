package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private val context: Context) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){
    var reviews = mutableListOf<ReviewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_review,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() : Int = reviews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }
    
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val text : TextView = itemView.findViewById(R.id.review_text)

        fun bind(item:ReviewData){
            text.text = item.text
        }
    }

}