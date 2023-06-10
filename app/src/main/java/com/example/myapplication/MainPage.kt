package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPage : AppCompatActivity() {
    private lateinit var btn_posting: Button
    private lateinit var btn_category: Button
    private lateinit var btn_myposting: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        btn_posting=findViewById(R.id.posting_page)
        btn_category=findViewById(R.id.category_page)
        btn_myposting=findViewById(R.id.my_post_page)

        btn_posting.setOnClickListener{
            val intent= Intent(this, PostingPage::class.java)
            startActivity(intent)
        }

        btn_category.setOnClickListener{
            val intent= Intent(this, CategoryPage::class.java)
            startActivity(intent)
        }

        btn_myposting.setOnClickListener{
            val intent= Intent(this, MyPost::class.java)
            startActivity(intent)
        }
    }




}