package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_mate.view.*

interface OnClickListener{
    fun btnClick(holder: MateAdapter.ViewHolder?, view: View, position: Int)
}

class MateAdapter(private val context: Context) : RecyclerView.Adapter<MateAdapter.ViewHolder>(){
    var mates = mutableListOf<MateData>()
    lateinit var listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mate,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() : Int = mates.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mates[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        init{
            itemView.mate_review_button.setOnClickListener {
                listener?.btnClick(this,itemView,adapterPosition)
            }
        }

        fun bind(item: MateData){
            itemView.mate_username.text = item.name
            Glide.with(context).load(item.imageURL).circleCrop().into(itemView.mate_profile_image)
        }

    }

}
