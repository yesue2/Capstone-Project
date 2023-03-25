package com.example.myapplication

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.android.synthetic.main.row_chat.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CharAdapter : RecyclerView.Adapter<CharAdapter.ViewHolder>(){
    var itemList = ArrayList<ChatData>()
    var UsersIDMap = HashMap<String, Any>()
    var myNickname = ""
    lateinit var context: ChatActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_chat, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun setItem(item: ChatData):Unit{
            itemView.chat_nickname.text = item.nickname
            itemView.chat_msg.text = item.msg
            itemView.chat_time.text = item.time
            itemView.chat_msg.setPadding(10, 20, 10, 20)


            if(itemView.chat_nickname.text.equals(myNickname)){
                //itemView.chat_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END)
                //itemView.chat_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END)
                itemView.chat_text_background.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.f7d794))
                itemView.chat_nickname.text = ""
                itemView.chat_image.setImageResource(R.drawable.icon_none)
                itemView.chat_main_layout.gravity = 5
                itemView.chat_msg.setTextColor(Color.BLACK)

            }else{
                itemView.chat_text_background.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.F46E6E))
                itemView.chat_nickname.text = item.nickname
                //itemView.chat_image.setImageResource(R.drawable.icon)
                Glide.with(context).load("https://firebasestorage.googleapis.com/v0/b/banana-8d3ab.appspot.com/o/Image%2F${UsersIDMap.get(item.nickname)}?alt=media&token=05d7ec83-54a0-48fe-9b0f-cb1c3c92a4ad").circleCrop().into(itemView.chat_image);
                itemView.chat_main_layout.gravity = 0
                itemView.chat_msg.setTextColor(Color.WHITE)
            }
        }
    }
}