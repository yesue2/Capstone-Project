package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_posting.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.brand_name.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PostingPage : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var uid: String

    private lateinit var spinnerWriteType: Spinner
    private lateinit var writeContent: EditText

    private lateinit var writeKey: ArrayList<String>
    private lateinit var writeValue: ArrayList<String>

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var storeList: ArrayList<Store>

    private lateinit var postingAdapter: PostingAdapter

    private lateinit var S_btn: Button

    private lateinit var selectedCategory: String

    private var selectedStoreName: String? = null

    interface OnStoreClickListener {
        fun onStoreClick(store: Store)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posting)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        val user: FirebaseUser? = mAuth.currentUser
        uid = user?.uid ?: ""

        spinnerWriteType = findViewById(R.id.spinner_write_type)
        writeContent = findViewById(R.id.content)

        S_btn = findViewById(R.id.select_type)
        S_btn.setOnClickListener {
            val index = spinnerWriteType.selectedItemPosition
            selectedCategory = writeKey[index]
            fetchStoreList(selectedCategory)

            Log.d("PostingPage", "Selected Category: $selectedCategory")
        }

        writeKey = ArrayList()
        writeValue = ArrayList()
        storeList = ArrayList()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        postingAdapter = PostingAdapter(storeList, object : PostingAdapter.OnStoreClickListener {
            override fun onStoreClick(store: Store) {
                selectedStoreName = store.storeName
                selected_item_tv.text = "선택된 가게: ${store.storeName}"
                postingAdapter.notifyDataSetChanged()
            }
        }, selectedStoreName)


        recyclerView.adapter = postingAdapter

        getBoardType()
    }


    private fun getBoardType() {
        mDatabase.child("foodList").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataArray: ArrayList<Map<String, String>>? = dataSnapshot.value as? ArrayList<Map<String, String>>

                writeKey.clear()
                writeValue.clear()

                val categorySet = HashSet<String>()

                dataArray?.let {
                    for (item in it) {
                        val category = item["cate"]
                        category?.let {
                            categorySet.add(category)
                        }
                    }
                }

                writeKey.addAll(categorySet)
                writeValue.addAll(categorySet)

                adapter = ArrayAdapter(this@PostingPage, android.R.layout.simple_spinner_item, writeValue)
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                spinnerWriteType.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }
        })
    }

    private fun fetchStoreList(category: String) {
        mDatabase.child("foodList")
            .orderByChild("cate")
            .equalTo(category)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    storeList.clear()
                    for (storeSnapshot in dataSnapshot.children) {
                        val storeName = storeSnapshot.child("store_name").value as String?
                        val minOrder = storeSnapshot.child("min_order").value as String?
                        val timeTaken = storeSnapshot.child("time_taken").value as String?

                        storeName?.let {
                            val store = Store(storeName, minOrder, timeTaken)
                            storeList.add(store)
                        }
                    }
                    postingAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error
                }
            })
    }

    @SuppressLint("MissingInflatedId")
    fun buttonBoardRegister(view: View) {
        if (writeContent.text.toString() == "") {
            Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedStoreName.isNullOrEmpty()) {
            Toast.makeText(this, "가게를 선택하세요", Toast.LENGTH_SHORT).show()
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
