package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.android.synthetic.main.row_chat.view.*
import kotlin.reflect.typeOf


class MainPage : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    val adapter = ButtonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        //마이페이지 클릭이벤트 구현
        var auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/matching-72523.appspot.com/o/Image%2F${uid}?alt=media&token=30debee5-947f-4918-ba9c-3ee40e82f6e0")
            .circleCrop().into(icon_mypage);

        icon_mypage.setOnClickListener {
            val intent = Intent(this, MyPage::class.java)
            startActivity(intent)
        }


        //카테고리 구현
        var database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference()
        //databaseReference.child("categories").removeValue()
        addCategory("0", "icon_sushi","돈까스/회/일식")
        addCategory("1", "icon_chinese_food","중식")
        addCategory("2", "icon_chicken","치킨")
        addCategory("3", "icon_rice","백반/죽/국수")
        addCategory("4", "icon_dessert","카페/디저트")
        addCategory("5", "icon_hot_dog","분식")
        addCategory("6", "icon_zzim","찜/탕/찌개")
        addCategory("7", "icon_pizza","피자")
        addCategory("8", "icon_western_food","양식")
        addCategory("9", "icon_meat","고기/구이")
        addCategory("10", "icon_pig","족발/보쌈")
        addCategory("11", "icon_asian","아시안")
        addCategory("12", "icon_buger","패스트푸드")
        addCategory("13", "icon_lunch","도시락")

        //레이아웃의 스크롤 방향을 관리하는
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        //여기서 버튼들을 추가
        adapter.context = this
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                dataSnapshot.children.forEach{
                    if(it.key == "categories"){
                        var list:ArrayList<Any?> = it.value as ArrayList<Any?>
                        var count:Int = 1
                        var data1:String? = ""
                        var data2:String? = ""

                        for (i in list){
                            var map = i as HashMap<String, String>

                            if(count % 2 != 0){
                                data1 = map["filename"]
                                data2 = map["name"]
                            }
                            else if(count % 2 == 0){
                                adapter.itemList.add(MainButton(data1, data2, map["filename"], map["name"]))
                            }
                            count++
                        }
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        databaseReference.addValueEventListener(postListener)
        //색상 추가
        adapter.colorList.add(R.color.e0d0f0)
//        <color name="c63359a">#63359a</color>
//        <color name="c743cb3">#743cb3</color>
//        <color name="ca585d4">#a585d4</color>
//        <color name="e0d0f0">#e0d0f0</color>
//        <color name="ce4ddf2">#e4ddf2</color>
//        <color name="cfaf3fb">#faf3fb</color>
        recyclerView.adapter = adapter


        //카테고리정보저장
        var cate="string"
        val intent = Intent(this, CategoryPage::class.java)



        //각 버튼들이 클릭했을 때
        adapter.listener = object : OnButtonItemClickListener{
            override fun onItemClick(holder: ButtonAdapter.ViewHolder?, view: View, position: Int, index:Int) {
                var name:String?
                if(index == 0) {
                    name = adapter.itemList[position].name1
                    //showToast("아이템 클릭됨 : ${adapter.itemList[position].name1}")
                    cate=adapter.itemList[position].name1.toString()
                    intent.putExtra("key1", cate.toString())
                    startActivity(intent)

                }else{
                    name = adapter.itemList[position].name1
                   // showToast("아이템 클릭됨 : ${adapter.itemList[position].name2}")
                    cate=adapter.itemList[position].name2.toString()
                    intent.putExtra("key1", cate.toString())
                    startActivity(intent)

                }

//                when(name){
//                    "Pizza" -> {
//                    }
//                    else -> {
//                        showToast("main button error")
//                    }
//                }
            }
        }
    }

    fun showToast(msg:String){
        //Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun addCategory(id:String, filename:String?, name:String?){
        var category:Category = Category(filename, name)

        databaseReference.child("categories").child(id).setValue(category)
    }

}