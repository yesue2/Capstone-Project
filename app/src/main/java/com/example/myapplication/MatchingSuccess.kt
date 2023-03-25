package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
//import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_matching_success.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.brand_name.*
import org.w3c.dom.Text

class MatchingSuccess : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var userReference: DatabaseReference
    lateinit var imageIv:ImageView

    lateinit var storage:FirebaseStorage
    lateinit var firestore:FirebaseStorage

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_success)

        var database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference()
        userReference = database.getReference()

        var nickname="nickname"
        var userid="id"
        userid= FirebaseAuth.getInstance().currentUser?.uid.toString()

        userReference.child("Users").child(userid).child("userNickname")
            .get().addOnSuccessListener{
            nickname= it.value.toString()
        }



        databaseReference=database.getReference("MatchingUsers")

        //### intent넘겨주기 실험용 밑에
//        val grade = intent.getStringExtra("grade" )
//        val category = intent.getStringExtra("category" )
//        val arr = intent.getSerializableExtra("brandList") as ArrayList<String>
        //###


        val teamid=intent.getStringExtra("teamID") //임시로 카테고리불러오기
        val category=intent.getStringExtra("category") //임시로 카테고리불러오기

        var sendCate="임시카테고리명"



        when (category){
            "meat"->{sendCate="고기/구이"}
            "rice"->{sendCate= "도시락"}
            "sushi"->{sendCate= "돈까스/회/일식"}
            "lunch"->{sendCate= "백반/죽/국수"}
            "hotdog"->{sendCate= "분식"}
            "asian"->{sendCate= "아시안"}
            "western"->{sendCate= "양식"}
            "pig"->{sendCate= "족발/보쌈"}
            "chinese"->{sendCate= "중식"}
            "zzim"->{sendCate= "찜/탕/찌개"}
            "chicken"->{sendCate= "치킨"}
            "dessert"-> {sendCate= "카페/디저트" }
            "burger"->{sendCate= "패스트푸드"}
            "pizza"->{ sendCate= "피자"}
        }

        var tv=findViewById(R.id.tv2) as TextView
        tv.setText(sendCate.toString() )


        //##유저불러오기
        var i=0


        val btn_back=findViewById<Button>(R.id.btn_back) //뒤로가기버튼
        btn_back.setOnClickListener({

            val intent= Intent(this, MainPage::class.java) //다른 메뉴 찾아보기

            startActivity(intent)
        })

        val btn_done=findViewById<Button>(R.id.btn_done) //매칭 완료 채팅방생성
        btn_done.setOnClickListener({

            val intent= Intent(this, ChatActivity::class.java)
            intent.putExtra("nickname", nickname.toString())

            intent.putExtra("teamid", teamid.toString())

            Log.e(userid,nickname)
            startActivity(intent)
        })
    }

    fun showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}

