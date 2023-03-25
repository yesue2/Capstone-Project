package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth
    // mate recyclerview
    private lateinit var mateAdapter: MateAdapter
    private val mates = mutableListOf<MateData>()
    lateinit var userMate : RecyclerView
    lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        button = findViewById(R.id.not_review_button)
        var mateList : MutableList<String> = mutableListOf()
        val mateHash = intent.getSerializableExtra("userMap") as HashMap<*, *>?
        if (mateHash != null) {
            for(mate in mateHash.values){
                if(mate.toString().equals(uid)){
                    break
                }
                mateList.add(mate.toString())
            }
        }
        var mateNum = mateList.size

        // mate adapter
        mateAdapter = MateAdapter(this)
        user_mate.adapter = mateAdapter
        var username : String = ""
        var imageURL = ""

        for(i in 0 until mateNum) {
            val mate =
                FirebaseDatabase.getInstance().getReference("Users").child(mateList[i])
            mate.get().addOnSuccessListener { dataSnapshot ->
                username = dataSnapshot.child("userNickname").value.toString()
                imageURL =
                    "https://firebasestorage.googleapis.com/v0/b/banana-8d3ab.appspot.com/o/Image%2F" +
                            "${mateList[i]}?alt=media"
                mates.apply {
                    add(MateData(mateList[i], username, imageURL))
                    mateAdapter.listener = object : OnClickListener {
                        override fun btnClick(
                            holder: MateAdapter.ViewHolder?,
                            view: View,
                            position: Int
                        ) {
                            val intent = Intent(view.context, Review::class.java)
                            intent.putExtra("mate_id", mateList[i])
                            startActivity(intent)
                        }
                    }
                    mateAdapter.mates = mates
                    mateAdapter.notifyDataSetChanged()
                }
            }
        }
        /*mates.apply {
            for (i in 0 until mateNum) {
                val mate =
                    FirebaseDatabase.getInstance().getReference("Users").child(mateList[i])
                println("print ${mateList[i]}")
                mate.get().addOnSuccessListener { dataSnapshot ->
                    username = dataSnapshot.child("userNickname").value.toString()
                    println("$username")
                    imageURL = "https://firebasestorage.googleapis.com/v0/b/banana-8d3ab.appspot.com/o/Image%2F" +
                    "${mateList[i]}.jpg?alt=media"
                    println("뭐 $imageURL")
                    add(MateData(mateList[i], username, imageURL))
                    mateAdapter.listener = object : OnClickListener {
                        override fun btnClick(
                            holder: MateAdapter.ViewHolder?,
                            view: View,
                            position: Int
                        ) {
                            val intent = Intent(view.context, Review::class.java)
                            intent.putExtra("mate_id", mateList[i])
                            startActivity(intent)
                        }
                    }
                }
            }

            mateAdapter.mates = mates
            mateAdapter.notifyDataSetChanged()


        }*/
        button.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)
        }
    }

}