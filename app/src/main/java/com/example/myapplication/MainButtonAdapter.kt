package com.example.myapplication

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.mainpage_item.view.*

interface OnButtonItemClickListener{
    fun onItemClick(holder: ButtonAdapter.ViewHolder?, view: View, position: Int, index:Int)
}

class ButtonAdapter : RecyclerView.Adapter<ButtonAdapter.ViewHolder>(){
    var itemList = ArrayList<MainButton>()
    lateinit var listener: OnButtonItemClickListener
    var colorList = ArrayList<Int>()
    var colorIdx = 0
    lateinit var context: MainPage

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mainpage_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.foodButton1.setOnClickListener {
                listener?.onItemClick(this, itemView, adapterPosition, 0)
            }
            itemView.foodButton2.setOnClickListener {
                listener?.onItemClick(this, itemView, adapterPosition, 1)
            }
        }

        fun setItem(item: MainButton):Unit{
            //이름 설정
            itemView.foodName1.text = item.name1
            itemView.foodName2.text = item.name2

            //색상 설정
            itemView.foodButton1.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorList[colorIdx++]))
            if(colorIdx == colorList.size){
                colorIdx = 0
            }
            itemView.foodButton2.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorList[colorIdx++]))
            if(colorIdx == colorList.size){
                colorIdx = 0
            }

            //이미지 설정
            when(item.filename1){
                "icon_pizza" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_pizza)
                }
                "icon_asian" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_asian)
                }
                "icon_buger" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_buger)
                }
                "icon_chicken" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_chicken)
                }
                "icon_chinese_food" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_chinese_food)
                }
                "icon_dessert" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_dessert)
                }
                "icon_hot_dog" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_hot_dog)
                }
                "icon_lunch" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_lunch)
                }
                "icon_meat" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_meat)
                }
                "icon_pig" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_pig)
                }
                "icon_rice" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_rice)
                }
                "icon_sushi" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_sushi)
                }
                "icon_western_food" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_western_food)
                }
                "icon_zzim" -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon_zzim)
                }

                else -> {
                    itemView.foodButton1.setImageResource(R.drawable.icon)
                }
            }

            when(item.filename2){
                "icon_pizza" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_pizza)
                }
                "icon_asian" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_asian)
                }
                "icon_buger" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_buger)
                }
                "icon_chicken" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_chicken)
                }
                "icon_chinese_food" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_chinese_food)
                }
                "icon_dessert" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_dessert)
                }
                "icon_hot_dog" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_hot_dog)
                }
                "icon_lunch" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_lunch)
                }
                "icon_meat" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_meat)
                }
                "icon_pig" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_pig)
                }
                "icon_rice" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_rice)
                }
                "icon_sushi" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_sushi)
                }
                "icon_western_food" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_western_food)
                }
                "icon_zzim" -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon_zzim)
                }
                else -> {
                    itemView.foodButton2.setImageResource(R.drawable.icon)
                }
            }

        }
    }
}