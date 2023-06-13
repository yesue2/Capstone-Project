package com.example.myapplication

import PostAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MyPost : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_post)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter()
        recyclerView.adapter = postAdapter

        currentUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference = FirebaseDatabase.getInstance().getReference("board")

        loadMyPosts()
    }

    private fun loadMyPosts() {
        val userId = currentUser.uid
        val query = databaseReference.orderByChild("uid").equalTo(userId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val postList = ArrayList<Board>() // postList를 지역 변수로 선언하여 데이터를 저장
                for (snapshot in dataSnapshot.children) {
                    val board = snapshot.getValue(Board::class.java)
                    board?.let {
                        postList.add(it)
                    }
                }
                postAdapter.setData(postList) // 어댑터에 데이터 설정
                postAdapter.notifyDataSetChanged() // 어댑터에 변경된 데이터 알리기
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 처리 중 오류 발생 시 동작
            }
        })
    }
}


