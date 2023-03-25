package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.matching.MatchLoading
import com.example.myapplication.matching.Matching
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
//import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_category.view.*
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.brand_name.*



class CategoryPage : AppCompatActivity() {
    private lateinit var adapter: BrandAdapter
    lateinit var databaseReference: DatabaseReference
    lateinit var userReference: DatabaseReference


   // private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        var database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference()

        var userid="id" //유저아이디, 별점은 선택시 전송하는걸로
        var grade="3.5"

        //##유저아이디###########
        userid= FirebaseAuth.getInstance().currentUser?.uid.toString()
        databaseReference.child("Users").child(userid).child("userGrade")
            .get().addOnSuccessListener{
            grade= it.value.toString()
        }
//        //######################

        userReference=database.getReference("resData")

        var i=0
        var resCate="13"  //임시
        var sendCate="임시"

        var value=intent.getStringExtra("key1") //
        Log.e("snap",value.toString())
        when (value){
            "고기/구이"->{resCate= "0"; sendCate="meat"}
            "도시락"->{resCate= "1";sendCate= "rice"}
            "돈까스/회/일식"->{resCate= "2";sendCate= "sushi"}
            "백반/죽/국수"->{resCate= "3";sendCate= "lunch"}
            "분식"->{resCate= "4" ;sendCate= "hotdog"}
            "아시안"->{resCate= "5";sendCate= "asian"}
            "양식"->{resCate= "6";sendCate= "western"}
            "족발/보쌈"->{resCate= "7" ; sendCate= "pig"}
            "중식"->{resCate= "8";sendCate= "chinese"}
            "찜/탕/찌개"->{resCate= "9";sendCate= "zzim"}
            "치킨"->{resCate= "10";sendCate= "chicken"}
            "카페/디저트"-> {resCate= "11"; sendCate= "dessert" }
            "패스트푸드"->{resCate= "12";sendCate= "burger"}
            "피자"->{ resCate = "13";sendCate= "pizza"}
        }



        val rescateName=value.toString()


        //####

        adapter = BrandAdapter(this)

        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        var  recycleView:RecyclerView= findViewById(R.id.recycleView)

        recycleView.layoutManager = layoutManager

        recycleView.adapter=adapter

        var waitUserNum=0
        databaseReference.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //val test=snapshot.child("WaitUsers")
                waitUserNum= snapshot.child("WaitUsers").child(resCate)
                    .child("waitUserNum")
                    .value.toString().toInt()

                Log.e("qwer",waitUserNum.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        //#############################카테고리불러오기###########//


        adapter.brandList.add(Brand("상관없음","0","0","0"))



        userReference.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val test = snapshot.child(i.toString())
                    for (es in test.children) {
                        val data= Brand(
                            test.child("name").value.toString(),
                            test.child("cate").value.toString(),
                            test.child("cate_num").value.toString(),
                            test.child("num").value.toString()
                        )
                        if (es.key.toString()=="cate_num"){
                            val tempkey:String=es.value.toString()
                            if(tempkey==resCate){
                                adapter.brandList.add(data)
                            }
                        }
                    }
                    i++
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //#############################카테고리불러오기끝###########//



        var arr= arrayListOf<String>("0","0","0")

        //버튼
        val btn_search=findViewById<Button>(R.id.btn_search) //매칭 시작 버튼 일단 메인페이지가게설정
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


            val intent = Intent(this, MatchLoading::class.java)
            intent.putExtra("grade", grade.toString())
            intent.putExtra("brandList", arr)
            intent.putExtra("category", sendCate.toString())
            intent.putExtra("failedNum",0)

            //val arr = intent.getSerializableExtra("brandList") as ArrayList<String>
            //브랜드리스트는 위와같이 받아오면됨! 이후 arr[0], arr[1]등 사용가능
            startActivity(intent)
        }

        val btn_again=findViewById<Button>(R.id.btn_again) //다시하기버튼 메인페이지로
        btn_again.setOnClickListener({
            databaseReference.child("WaitUsers").child(resCate)
                .child(waitUserNum.toString()).removeValue() //유저데이터초기화
            //waitUserNum--
            val intent=Intent(this, MainPage::class.java)
            startActivity(intent)

        })


        var text:String
        var cate:String
        var cate_num:String
        var num:String

        var count=0 //브랜드 최대 3개선택
        var temp=0


        adapter.listener = object: OnBrandClickListener {
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
                if(checkStatus.get(position,true)){
                    if (count <3 &&view != null) {
                        view.setBackgroundColor(Color.YELLOW)
                        count++

                        text= text_name.toString()
                        cate= text_cate.toString()
                        cate_num= text_cate_num.toString()
                        num= text_num.toString()


                        var i=0
                        var temp="0"
                        while(i<3){
                            if (arr[i]=="0") {
                                temp = (i + 1).toString()
                                arr[i]=cate_num
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
                }
                else{ //클릭시 삭제하기. true 처음엔 true상태
                    if (view != null) {
                        view.setBackgroundColor(Color.WHITE)
                        i=0
                        while(i<3){
                            if(arr[i]==text_num) {
                                databaseReference
                                    .child("WaitUsers").child(resCate)
                                    .child(waitUserNum.toString()).child("brandList")
                                    .child((i + 1).toString()).removeValue() //올라간데이터를삭제해줌
                                arr[i]="0"
                                count--
                                break
                            }
                            i++
                        }
                        checkStatus.put(position,true)
                    }
                }
            }
        }
    }




    fun showToast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }


}