package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_post.*

class PostPage : AppCompatActivity() {
    private lateinit var btn_chat: Button
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        btn_chat=findViewById(R.id.chat_button)

        val title = intent.getStringExtra("title") ?: ""
        val date = intent.getStringExtra("date") ?: ""
        val content = intent.getStringExtra("content") ?: ""
        uid = intent.getStringExtra("uid") ?: ""

        title_tv.text = title
        date_tv.text = date
        content_tv.text = content

        btn_chat.setOnClickListener{
            val intent= Intent(this, ChatActivity::class.java)
            intent.putExtra("uid", uid)
            Log.d("PostPage", "uid: $uid")
            startActivity(intent)
        }
    }
}
