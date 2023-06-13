package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {

    private lateinit var mateAdapter: MateAdapter
    private val mates = mutableListOf<MateData>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var button: Button
    private lateinit var userUid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        recyclerView = findViewById(R.id.user_mate)
        button = findViewById(R.id.not_review_button)

        userUid = intent.getStringExtra("userUid") ?: ""

        // mate adapter
        mateAdapter = MateAdapter(this)
        recyclerView.adapter = mateAdapter

        val mateRef = FirebaseDatabase.getInstance().getReference("Users").child(userUid)
        mateRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val username = dataSnapshot.child("userNickname").value.toString()
                val imageURL = "https://firebasestorage.googleapis.com/v0/b/matching-72523.appspot.com/o/Image%2F${userUid}?alt=media"
                mates.add(MateData(userUid, username, imageURL))
                mateAdapter.listener = object : OnClickListener {
                    override fun btnClick(holder: MateAdapter.ViewHolder?, view: View, position: Int) {
                        val intent = Intent(view.context, Review::class.java)
                        intent.putExtra("mate_id", userUid)
                        startActivity(intent)
                    }
                }
                mateAdapter.mates = mates
                mateAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 처리할 내용
            }
        })

        button.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)
        }
    }
}

