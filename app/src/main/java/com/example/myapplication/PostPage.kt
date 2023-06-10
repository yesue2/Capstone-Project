package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_post.*

class PostPage : AppCompatActivity() {
    private lateinit var btn_chat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        btn_chat=findViewById(R.id.chat_button)

        val title = intent.getStringExtra("title") ?: ""
        val date = intent.getStringExtra("date") ?: ""
        val content = intent.getStringExtra("content") ?: ""

        title_tv.text = title
        date_tv.text = date
        content_tv.text = content

        btn_chat.setOnClickListener{
            val intent= Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}
