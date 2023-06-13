package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_page.*
import com.google.firebase.database.*
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import android.view.View
import android.widget.ImageView

class MainPage : AppCompatActivity() {
    private lateinit var btn_posting: Button
    private lateinit var btn_category: Button
    private lateinit var btn_myposting: Button
    private lateinit var btn_mypage: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        var auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()

        btn_posting=findViewById(R.id.posting_page)
        btn_category=findViewById(R.id.category_page)
        btn_myposting=findViewById(R.id.my_post_page)
        btn_mypage = findViewById(R.id.btn_mypage)

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/matching-72523.appspot.com/o/Image%2F${uid}?alt=media&token=30debee5-947f-4918-ba9c-3ee40e82f6e0").circleCrop().into(btn_mypage);

        btn_mypage.setOnClickListener {
            val intent = Intent(this, MyPage::class.java)
            startActivity(intent)
        }

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