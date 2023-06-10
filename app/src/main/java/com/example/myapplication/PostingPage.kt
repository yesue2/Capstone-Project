package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.brand_name.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PostingPage : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var uid: String

    private lateinit var spinnerWriteType: Spinner
    private lateinit var writeTitle: EditText
    private lateinit var writeContent: EditText

    private lateinit var writeKey: ArrayList<String>
    private lateinit var writeValue: ArrayList<String>

    private lateinit var adapter: ArrayAdapter<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posting)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        val user: FirebaseUser? = mAuth.currentUser
        uid = user?.uid ?: ""

        spinnerWriteType = findViewById(R.id.spinner_write_type)
        writeTitle = findViewById(R.id.title)
        writeContent = findViewById(R.id.content)

        writeKey = ArrayList()
        writeValue = ArrayList()

        getBoardType()
    }

    private fun getBoardType() {
        mDatabase.child("foodType").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataArray: ArrayList<Map<String, String>>? = dataSnapshot.value as? ArrayList<Map<String, String>>

                writeKey.clear()
                writeValue.clear()

                dataArray?.let {
                    for (item in it) {
                        val category = item["cate"]
                        category?.let {
                            writeKey.add(category)
                            writeValue.add(category)
                        }
                    }
                }

                adapter = ArrayAdapter(this@PostingPage, android.R.layout.simple_spinner_item, writeValue)
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                spinnerWriteType.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }
        })
    }

    @SuppressLint("MissingInflatedId")
    fun buttonBoardRegister(view: View) {
        if (writeTitle.text.toString() == "") {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (writeContent.text.toString() == "") {
            Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show()
            return
        }

        val index = spinnerWriteType.selectedItemPosition

        val selectedCategory = writeKey[index] // 선택한 가게 카테고리의 키 값
        val selectedCategoryName = writeValue[index] // 선택한 가게 카테고리의 이름

        // 선택한 가게 카테고리를 사용하여 필요한 로직을 수행하면 됩니다.

        val userModel = UserModel(uid, nickname = null)
        val user = userModel.nickname

        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val dateFormat2 = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())

        val time = Date()

        val today = dateFormat.format(time)
        val orderToday = dateFormat2.format(time)

        val board = Board().apply {
            title = writeTitle.text.toString()
            content = writeContent.text.toString()
            contentType = selectedCategory
            uid = this@PostingPage.uid
            name = user
            date = today
            orderDate = orderToday
        }

        mDatabase.child("board").child(selectedCategory).child(uid).push().setValue(board)

        val i = Intent(this@PostingPage, MainPage::class.java)
        startActivity(i)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

