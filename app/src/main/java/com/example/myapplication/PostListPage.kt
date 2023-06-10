package com.example.myapplication

import PostAdapter
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_category.*

class PostListPage : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: PostAdapter
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        category = intent.getStringExtra("key1") ?: ""

        databaseReference = FirebaseDatabase.getInstance().reference.child("board").child(category)

        adapter = PostAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadPosts()

        adapter.setOnItemClickListener(object : PostAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val post = adapter.getItem(position)
                post.title?.let { post.date?.let { it1 ->
                    post.content?.let { it2 ->
                        navigateToPostPage(it,
                            it1, it2
                        )
                    }
                } }
                // Handle item click, e.g., open post details activity
            }
        })
    }

    private fun loadPosts() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val postList = ArrayList<Board>()

                if (dataSnapshot.exists()) {
                    for (categorySnapshot in dataSnapshot.children) {
                        for (postSnapshot in categorySnapshot.children) {
                            val post = postSnapshot.getValue(Board::class.java)
                            post?.let { postList.add(it) }
                        }
                    }
                }

                val postCount = postList.size
                Log.d(TAG, "Posts found for the selected category: $postCount")

                adapter.setData(postList)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }
        })
    }
    private fun navigateToPostPage(title: String, date: String, content: String) {
        val intent = Intent(this, PostPage::class.java)
        intent.putExtra("title", title)
        intent.putExtra("date", date)
        intent.putExtra("content", content)
        startActivity(intent)
    }
}
