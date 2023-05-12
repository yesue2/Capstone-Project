package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityCategoryBinding
import com.example.myapplication.matching.MatchingLoad
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.brand_name.*


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

        var resCate = "13"  //임시
        var sendCate = "임시"

        when (value) {
            "고기/구이" -> {
                resCate = "0"; sendCate = "meat"
            }
            "도시락" -> {
                resCate = "1";sendCate = "rice"
            }
            "돈까스/회/일식" -> {
                resCate = "2";sendCate = "sushi"
            }
            "백반/죽/국수" -> {
                resCate = "3";sendCate = "lunch"
            }
            "분식" -> {
                resCate = "4";sendCate = "hotdog"
            }
            "아시안" -> {
                resCate = "5";sendCate = "asian"
            }
            "양식" -> {
                resCate = "6";sendCate = "western"
            }
            "족발/보쌈" -> {
                resCate = "7"; sendCate = "pig"
            }
            "중식" -> {
                resCate = "8";sendCate = "chinese"
            }
            "찜/탕/찌개" -> {
                resCate = "9";sendCate = "zzim"
            }
            "치킨" -> {
                resCate = "10";sendCate = "chicken"
            }
            "카페/디저트" -> {
                resCate = "11"; sendCate = "dessert"
            }
            "패스트푸드" -> {
                resCate = "12";sendCate = "burger"
            }
            "피자" -> {
                resCate = "13";sendCate = "pizza"
            }
        }

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

        var waitUserNum = 0
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //val test=snapshot.child("WaitUsers")
                val waitUserNumValue = snapshot.child("WaitUsers").child(resCate)
                    .child("waitUserNum")
                    .value

                waitUserNum = if (waitUserNumValue != null) {
                    waitUserNumValue.toString().toInt()
                } else {
                    0 // 또는 적절한 기본값
                }


                //에러 보고용 로그
                Log.e("qwer", waitUserNum.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        var arr = arrayListOf<String>("0", "0", "0")

        //버튼
        val btn_search = findViewById<Button>(R.id.btn_search) //매칭 시작 버튼 일단 메인페이지가게설정
        btn_search.setOnClickListener {

            databaseReference.child("WaitUsers").child(resCate)
                .child(waitUserNum.toString()).child("uid").setValue(userid)

            databaseReference.child("WaitUsers").child(resCate)
                .child(waitUserNum.toString()).child("grade").setValue(grade)
            waitUserNum++
            databaseReference.child("WaitUsers").child(resCate)
                .child("waitUserNum").setValue(waitUserNum)


            Log.e("nowBrandList", arr.toString())
            //  Log.e("nowBrandList", arr[0].toString())


            val intent = Intent(this, MatchingLoad::class.java)
            intent.putExtra("grade", grade)
            intent.putExtra("brandList", arr)
            intent.putExtra("category", sendCate)
            intent.putExtra("failedNum", 0)

            //val arr = intent.getSerializableExtra("brandList") as ArrayList<String>
            //브랜드리스트는 위와같이 받아오면됨! 이후 arr[0], arr[1]등 사용가능
            startActivity(intent)
        }

        val btn_again = findViewById<Button>(R.id.btn_again) //다시하기버튼 메인페이지로
        btn_again.setOnClickListener({
            databaseReference.child("WaitUsers").child(resCate)
                .child(waitUserNum.toString()).removeValue() //유저데이터초기화
            //waitUserNum--
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)

        })


        var i = 0
        var text: String
        var cate: String
        var cate_num: String
        var num: String

        var count = 0 //브랜드 최대 3개선택
        var temp = 0


        adapter.listener = object : OnBrandClickListener {
            override fun onItemClick(
                holder: BrandAdapter.ViewHolder?,
                view: View?,
                position: Int,
                checkStatus: SparseBooleanArray,
                text_name: CharSequence,
                text_cate: CharSequence,
                text_num: CharSequence,
                text_cate_num: CharSequence,
                text5: CharSequence,
                text_grade: CharSequence
            ) {

                //3개 선택
                if (checkStatus.get(position, true)) {
                    if (count < 3 && view != null) {
                        view.setBackgroundColor(Color.YELLOW)
                        count++

                        text = text_name.toString()
                        cate = text_cate.toString()
                        cate_num = text_cate_num.toString()
                        num = text_num.toString()


                        var i = 0
                        var temp = "0"
                        while (i < 3) {
                            if (arr[i] == "0") {
                                temp = (i + 1).toString()
                                arr[i] = cate_num
                                break

                            }
                            i++
                        }
                        databaseReference.child("WaitUsers").child(resCate)
                            .child(waitUserNum.toString())
                            .child("brandList")

                            .child(temp).setValue(cate_num)
                        // 매칭을 위해 실시간데이터의 matchingUser데이터에 카테고리정보를 넣어줍니다
                        checkStatus.put(position, false)
                    }
                } else { //클릭시 삭제하기. true 처음엔 true상태
                    if (view != null) {
                        view.setBackgroundColor(Color.WHITE)
                        i = 0
                        while (i < 3) {
                            if (arr[i] == text_num) {
                                databaseReference
                                    .child("WaitUsers").child(resCate)
                                    .child(waitUserNum.toString()).child("brandList")
                                    .child((i + 1).toString()).removeValue() //올라간데이터를삭제해줌
                                arr[i] = "0"
                                count--
                                break
                            }
                            i++
                        }
                        checkStatus.put(position, true)
                    }
                }
            }
        }
    }
}