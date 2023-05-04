package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityCategoryBinding
import com.example.myapplication.matching.MatchLoading
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CategoryPage : AppCompatActivity() {
    lateinit var brandAdapter: BrandAdapter
    lateinit var databaseReference: DatabaseReference
    lateinit var userReference: DatabaseReference

    val binding by lazy { ActivityCategoryBinding.inflate(layoutInflater) }

    var adapter = BrandAdapter(this)
    var selectedBrands: MutableList<Any> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        var database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference()

        //####유저 데이터 받아오기 시작####

        var userid = "id" //유저아이디, 별점은 선택시 전송하는걸로
        var grade = "2.5"

        //userId 값 가져와서 userid 변수에 할당
        userid = FirebaseAuth.getInstance().currentUser?.uid.toString()

        //userGrade 값 가져와서 grade 변수에 할당
        /*get() 함수는 비동기식으로 실행되므로,
        가져온 값이 성공적으로 반환될 때 실행되는 addOnSuccessListener를 사용하여 값을 할당*/
        databaseReference.child("Users").child(userid).child("userGrade")
            .get().addOnSuccessListener {
                //grade 변수에 값을 할당하기 전에 TextView 객체가 null 인지 확인
                if (it != null && it.value != null) {
                    grade = it.value.toString()
                    Log.d("gradeValue", grade)
                }
            }
        //Log.d("gradeValue", grade)보다 먼저 logcat에 출력 -> grade 값을 비동기식으로 받아오기 때문
        Log.d("userIdValue", userid)


        //####브랜드데이터 받아오기 시작####

        //MainPage에서 key1 값 받아오기
        var value = intent.getStringExtra("key1")

        //Brand DB에서 value값과 같은 cate 값 가진 데이터 불러오기 -> ex)value가 '중식'이면 cate도 '중식'
        userReference = FirebaseDatabase.getInstance().getReference("Brand")


        // 브랜드 데이터 가져오기
        val brandList: MutableList<BrandModel> = mutableListOf()
        val brandListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                brandList.clear()
                for (data in snapshot.children) {
                    val item = data.getValue(BrandModel::class.java)
                    Log.d("CategoryPageActivity", "item: ${item}")
                    // 리스트에 읽어 온 데이터를 넣어준다.
                    item?.let { brandList.add(it) }
                }
                // notifyDataSetChanged()를 호출하여 adapter에게 값이 변경 되었음을 알려준다.
                brandAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        //addValueEventListener() 메서드로 userReference에 ValueEventListener를 추가한다.
        userReference.orderByChild("cate").equalTo(value).addValueEventListener(brandListener)

        brandAdapter = BrandAdapter(this)
        brandAdapter.brandList = brandList
        binding.recycleView.adapter = brandAdapter

        /* recyclerView Option */
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        //////////////////

        brandAdapter.setOnItemClickListener { brand ->
            // 선택된 가게 리스트에 추가
            if (selectedBrands.size <= 3) {
                selectedBrands.add(brand)
            }
            Log.d("CategoryPageActivity", selectedBrands.toString())
        }

        val button: Button = findViewById(R.id.btn_search)
        button.setOnClickListener {
            startMatching(selectedBrands)
        }
        val btn_again=findViewById<Button>(R.id.btn_again) //다시하기버튼 메인페이지로
        btn_again.setOnClickListener({
            val intent=Intent(this, MainPage::class.java)
            startActivity(intent)

        })
    }
    fun startMatching(selectedBrands: List<Any>) {
        // 선택된 가게 리스트를 가져오기
        val mySelectedBrands = selectedBrands.filterIsInstance<BrandModel>().map { it.name }

        Log.d("CategoryPageActivity", mySelectedBrands.toString())

        // 선택된 가게 리스트가 비어있으면 에러 메시지 띄우고 함수 종료
        if (mySelectedBrands.isEmpty()) {
            Toast.makeText(this, "최소 1개 이상의 가게를 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (mySelectedBrands.size >= 1) {
            Toast.makeText(this, "매칭이 시작되었습니다.", Toast.LENGTH_SHORT).show()
        }

        // 매칭을 위해 필요한 데이터를 가져오기
        val userReference = FirebaseDatabase.getInstance().getReference("Users")
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserId = currentUser?.uid
        var waitUsersRef = FirebaseDatabase.getInstance().getReference("WaitUsers")

        // 현재 사용자의 정보를 가져오기
        userReference.child(currentUserId!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)

                // WaitUsers 노드에 사용자 정보 추가하기
                val waitUser = WaitUserModel(currentUserId, user!!.nickname,
                    mySelectedBrands as ArrayList<String>
                )
                waitUsersRef.child(currentUserId).setValue(waitUser)

                // 매칭 대기 중인 사용자 찾기
                waitUsersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (waitUserSnapshot in snapshot.children) {
                            val waitUser = waitUserSnapshot.getValue(WaitUserModel::class.java)

                            if (waitUser != null && waitUser.uid != currentUserId && waitUser.brands != null) {
                                val intersection = waitUser.brands!!.intersect(mySelectedBrands)
                                if (intersection.isNotEmpty()) {
                                    waitUsersRef.child(currentUserId).removeValue()
                                    waitUsersRef.child(waitUser.uid.toString()).removeValue()

                                    val intent = Intent(this@CategoryPage, MatchLoading::class.java)
                                    intent.putExtra("mySelectedBrands", mySelectedBrands.toTypedArray())
                                    intent.putExtra("matchedUserId", waitUser.uid)
                                    startActivity(intent)

                                    return
                                }
                            }
                        }

                        // 매칭 대기 중인 사용자가 없는 경우, 매칭 실패 메시지 띄우기
                        Toast.makeText(this@CategoryPage, "매칭에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()

                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

}