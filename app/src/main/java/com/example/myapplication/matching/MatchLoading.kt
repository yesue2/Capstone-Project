package com.example.myapplication.matching

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.MatchingFailed
import com.example.myapplication.MatchingSuccess
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.mainpage_item.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonCancellable.cancel
import kotlin.concurrent.timer


class MatchLoading : AppCompatActivity() {

    private lateinit var loading: ImageView
    private lateinit var ctgImage : ImageView
    private var matching: Matching = Matching()
    private lateinit var auth: FirebaseAuth
    var waitUsersNum: Int = 0
    var isSuccess = false

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_loading)

        auth = FirebaseAuth.getInstance()
        ctgImage = findViewById(R.id.ctg_image)
        loading = findViewById(R.id.loading)
        // loading.gif 파일 불러오기
        Glide.with(this).load(R.raw.loading).circleCrop().into(loading)
        var category = intent.extras?.getString("category")
        var categoryNum : Int

        when(category) {
            "pizza" -> {
                Glide.with(this).load(R.drawable.icon_pizza).circleCrop().into(ctgImage)
                categoryNum = 13
            }
            "asian" -> {
                Glide.with(this).load(R.drawable.icon_asian).circleCrop().into(ctgImage)
                categoryNum = 5
            }
            "burger" -> {
                Glide.with(this).load(R.drawable.icon_buger).circleCrop().into(ctgImage)
                categoryNum = 12
            }
            "chicken" -> {
                Glide.with(this).load(R.drawable.icon_chicken).circleCrop().into(ctgImage)
                categoryNum = 10
            }
            "chinese" -> {
                Glide.with(this).load(R.drawable.icon_chinese_food).circleCrop().into(ctgImage)
                categoryNum = 8
            }
            "dessert" -> {
                Glide.with(this).load(R.drawable.icon_dessert).circleCrop().into(ctgImage)
                categoryNum = 11
            }
            "hotdog" -> {
                Glide.with(this).load(R.drawable.icon_hot_dog).circleCrop().into(ctgImage)
                categoryNum = 4
            }
            "lunch" -> {
                Glide.with(this).load(R.drawable.icon_lunch).circleCrop().into(ctgImage)
                categoryNum = 1
            }
            "meat" -> {
                Glide.with(this).load(R.drawable.icon_meat).circleCrop().into(ctgImage)
                categoryNum = 0
            }
            "pig" -> {
                Glide.with(this).load(R.drawable.icon_pig).circleCrop().into(ctgImage)
                categoryNum = 7
            }
            "rice" -> {
                Glide.with(this).load(R.drawable.icon_rice).circleCrop().into(ctgImage)
                categoryNum = 3
            }
            "sushi" -> {
                Glide.with(this).load(R.drawable.icon_sushi).circleCrop().into(ctgImage)
                categoryNum = 2
            }
            "western" -> {
                Glide.with(this).load(R.drawable.icon_western_food).circleCrop().into(ctgImage)
                categoryNum = 6
            }
            "zzim" -> {
                Glide.with(this).load(R.drawable.icon_zzim).circleCrop().into(ctgImage)
                categoryNum = 9
            }
            else -> {
                Glide.with(this).load(R.drawable.icon).circleCrop().into(ctgImage)
                categoryNum = -1
            }
        }

        var waitUsers = FirebaseDatabase.getInstance().getReference("WaitUsers").child("$categoryNum")
        var waitUserNum =
            FirebaseDatabase.getInstance().getReference("WaitUsers").child("$categoryNum").child("waitUserNum")
        val uid = auth.currentUser?.uid.toString()
        val finish = FirebaseDatabase.getInstance().getReference("FinishedMatch")

        // 매칭하기 버튼 클릭 시 intent로 넘어와야 할 값
        // 1. failedNum = 0 2. 선택한 brandList 3. grade
        // 매칭 실패 시 intent로 넘어와야 할 값
        // 1. failedNum = +1 2. 선택한 brandList(동일) 3. grade + 0.5f
        var failedNum = intent.extras?.getInt("failedNum").toString().toInt()
        var grade = intent.extras?.getFloat("grade").toString().toFloat()
        val brandList: ArrayList<Int>? = intent.extras?.getIntegerArrayList("brandList")

        waitUserNum.get().addOnSuccessListener {
            waitUsersNum = it.value.toString().toInt()
            /*println("userNum : ${it.value}")
            println("value : $waitUsersNum")*/
            // 실패한 유저가 다시 들어오는 경우에는 대기열에 새로 추가해줘야 함.
            if (failedNum > 0) {
                if (failedNum == 1) { // 실패한 횟수가 3번 이상이라면 매칭 실패 페이지로 이동
                    /*Toast.makeText(
                        this,
                        "매칭에 3번 이상 실패하여 매칭을 종료하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()*/
                    val failIntent = Intent(this, MatchingFailed::class.java)
                    failIntent.putExtra("failedNum", failedNum.toString())
                    failIntent.putExtra("grade", grade.toString())
                    failIntent.putExtra("brandList", brandList.toString())
                    finish.child(uid).removeValue()
                    startActivity(failIntent)
                    this.finish()
                    return@addOnSuccessListener
                } else {// 실패한 횟수가 3번 미만이라면 대기열에 다시 추가하기
                    /*Toast.makeText(
                        this,
                        "매칭을 $failedNum 번째 재시도합니다.",
                        Toast.LENGTH_SHORT
                    ).show()*/
                    waitUsers.child("$waitUsersNum").child("uid").setValue(uid) // waitUsers
                    waitUsers.child("$waitUsersNum").child("grade")
                        .setValue(grade)
                    waitUsers.child("$waitUsersNum").child("brandList").setValue(brandList)
                    waitUserNum.setValue(++waitUsersNum)
                }
            }
            if (waitUsersNum == 0) { // 실제 코드에서는 waitUserNum == 1, 첫 번째 대기자라면 총대
                Toast.makeText(
                    this,
                    "매칭을 시작합니다.",
                    Toast.LENGTH_SHORT
                ).show()
                Handler(Looper.getMainLooper()).postDelayed({ // 10초동안 대기자 모으고 계산.
                    waitUsers = FirebaseDatabase.getInstance().getReference("WaitUsers").child("$categoryNum")
                    waitUserNum =
                        FirebaseDatabase.getInstance().getReference("WaitUsers").child("$categoryNum").child("waitUserNum")
                    println("10초 후 WAITUSERNUM : $waitUsersNum")
                    match(matching, waitUsers, waitUsersNum, categoryNum) // 매칭 & 대기열 리셋

                    Handler(Looper.getMainLooper()).postDelayed({
                        // 성공한 사람들
                        for (team in matching.getMateList()) {
                            team.id = team.userList[0].uid
                            for (user in team.userList) {
                                // user matchedUsers push
                                /*println("성공한 userid : ${user.uid}")*/
                                finish.child(user.uid).child("isSuccess").setValue(true)
                                finish.child(user.uid).child("teamID").setValue(team.id)
                            }
                        }
                        // 실패한 사람들
                        for (failUser in matching.getFailUserList()) {
                            /*println("실패한 userid : ${failUser.uid}")*/
                            finish.child(failUser.uid).child("isSuccess").setValue(false)
                        }
                        if (category != null) {
                            isMatch(finish, uid, failedNum, grade, brandList!!, category)
                        }
                        cancel()
                    }, 3000)
                    cancel()
                }, 10000)
            } else { // 대기열에 추가만 되고 총대 x
                // 총대를 멘 user가 매칭을 끝낼 때까지 기다렸다가 finish에 자기 자신이 추가되면 나옴.
                Toast.makeText(
                    this,
                    "매칭 결과를 기다리고 있습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                timer(period = 1000, initialDelay = 10000) {
                    finish.get().addOnSuccessListener {
                        it.children.forEach {
                            if (uid.equals(it.key)) { // finish
                                if (category != null) {
                                    isMatch(finish, uid, failedNum, grade, brandList!!, category)
                                }
                                cancel()
                            }
                        }
                    }
                }
            }
        }
        return
    }

    private fun isMatch(
        finish: DatabaseReference,
        uid: String,
        failedNum: Int,
        grade: Float,
        brandList: ArrayList<Int>,
        category : String
    ) {
        finish.child(uid).child("isSuccess").get().addOnSuccessListener {
            isSuccess = it.value.toString().toBoolean()
            println("isSuccess : $isSuccess")
            // 내가 성공 인지 실패 인지 검사
            if (isSuccess) {
                Toast.makeText(
                    this,
                    "매칭에 성공했습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                val successIntent = Intent(this, MatchingSuccess::class.java)
                finish.child(uid).child("teamID").get().addOnSuccessListener {
                    successIntent.putExtra("teamID", it.value.toString())
                    successIntent.putExtra("category",category)
                    finish.child(uid).removeValue()
                    startActivity(successIntent)
                    this.finish()
                }
            } else {
                /*Toast.makeText(
                    this,
                    "매칭에 실패했습니다.",
                    Toast.LENGTH_SHORT
                ).show()*/
                this.finish()//인텐트 종료
                overridePendingTransition(0, 0) //인텐트 효과 없애기
                val recIntend = getIntent()
                recIntend.putExtra("failedNum", failedNum + 1)
                recIntend.putExtra("grade", grade+0.5f)
                recIntend.putExtra("brandList", brandList)
                recIntend.putExtra("category",category)
                finish.child(uid).removeValue()
                startActivity(recIntend) //액티비티 열기
                overridePendingTransition(0, 0);//인텐트 효과 없애기
            }
        }
        return
    }

    private fun match(matching: Matching, waitUsers: DatabaseReference, waitUsersNum: Int, categoryNum : Int) {

        // 초기화
        matching.initData(waitUsers, waitUsersNum)
        Handler(Looper.getMainLooper()).postDelayed({
            waitUsers.removeValue()
            FirebaseDatabase.getInstance().getReference("WaitUsers").child("$categoryNum").child("waitUserNum").setValue(0)
            // 정렬
            matching.sort()
            // print
            /*matching.getUserList().forEach { user ->
                println("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}")
            }
            println()*/

            // 선호도 테이블 생성
            matching.setPreferTable()
            // print
            /*matching.getUserList().forEach { user ->
                print("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}, preferTable=")
                user.preferTable.forEach { list ->
                    print("[ ")
                    list.forEach {
                        print(it.uid)
                    }
                    print(" ]")
                }
                println()
            }
            println()*/

            // 선택
            matching.choice()
            // print
            /*matching.getMateList().forEach { mate ->
                print("userList=[ ")
                mate.userList.forEach {
                    print(it.uid)
                }
                println(" ]")
            }
            println()*/

            // failUser failList에 저장
            matching.setFailUser()

            // print
            /*matching.getUserList().forEach { user ->
                println("uid=${user.uid}, grade=${user.grade}, rank=${user.rank}, brandList=${user.brandList}, fail=${user.fail}")
            }*/
        }, 3000)
        return
    }
}