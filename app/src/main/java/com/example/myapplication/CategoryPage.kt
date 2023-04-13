package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityCategoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.android.synthetic.main.brand_name.*


class CategoryPage : AppCompatActivity() {
    lateinit var brandAdapter: BrandAdapter
    lateinit var databaseReference: DatabaseReference
    lateinit var userReference: DatabaseReference

    val binding by lazy { ActivityCategoryBinding.inflate(layoutInflater) }

    var adapter = BrandAdapter()

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
                grade = it.value.toString()
                Log.d("gradeValue", grade)
            }
        //Log.d("gradeValue", grade)보다 먼저 logcat에 출력 -> grade 값을 비동기식으로 받아오기 때문
        Log.d("userIdValue", userid)


        //####브랜드데이터 받아오기 시작####

        //MainPage에서 key1 값 받아오기
        var value = intent.getStringExtra("key1")

        //Brand DB에서 value값과 같은 cate 값 가진 데이터 불러오기 -> ex)value가 '중식'이면 cate도 '중식'
        userReference = FirebaseDatabase.getInstance().getReference("Brand")

        val brandData = userReference.orderByChild("cate").equalTo(value)


        brandAdapter = BrandAdapter()
        brandAdapter.listData = getFBBrandData()
        binding.recycleView.adapter = brandAdapter

        /* recycyclerView Option */
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        // binding.recyclerView.layoutManager = LinearLayoutManager(this,
        //LinearLayoutManager.HORIZONTAL,false)
        // GridLayoutManager(this, 3)
        // StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
    }


    //#####파이어베이스에서 가게 데이터 가져오기#####
    private fun getFBBrandData(): MutableList<BrandModel> {
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
        userReference.addValueEventListener(brandListener)
        return brandList
    }

}



