package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: FirebaseDatabase

    private lateinit var R_btn: Button
    private lateinit var S_btn: Button
    private lateinit var A_btn:Button
    private lateinit var B_btn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        var Authnum=binding.AuthNum
        var Name=binding.Name
        var StudentID=binding.studentID
        var Email=binding.email
        var Password=binding.pwd
        var Password_check=binding.pwdCheck
        var Nickname=binding.nickname
        S_btn = findViewById(R.id.submit_btn)
        R_btn = findViewById(R.id.register_btn)
        A_btn=findViewById(R.id.Auth_button)
        B_btn=findViewById(R.id.back_btn)

        var userGrade:String="0" //Float= 0F
        var reviewNum:String="0" //Int=0
        var userReview= mutableListOf<String>()
        var mateList= mutableListOf<String>()

        var reviewMax:String="5" //Float=0F
        var reviewSum:String="0" //Float=0F
        var reviewMin:String="0" //Float=0F

        val MailFunction=MailSender() // MailSender의 클래스 객체 저장
        var check=false //인증번호가 맞다면 check값을 true로 지정하고 회원가입버튼 클릭시 이를 확인한다


        R_btn.setOnClickListener {
            //학번이 비어있을 때 1
            if (TextUtils.isEmpty(Name.text.toString()))
                Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()

            //닉네임이 비어있을 때
            else if (TextUtils.isEmpty(Nickname.text.toString()))
                Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()

            //이름이 비어있을 때 3
            else if (TextUtils.isEmpty(StudentID.text.toString()))
                Toast.makeText(this, "학번을 입력해주세요", Toast.LENGTH_SHORT).show()

            //학번 형식이 맞지 않을 때
            else if (StudentID.length() != 9)
                Toast.makeText(this, "학번 형식이 맞지 않습니다", Toast.LENGTH_SHORT).show()

            //이메일이 비어있을 때 2
            else if (TextUtils.isEmpty(Email.text.toString()))
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()

            else if(check!=true)
                Toast.makeText(this, "인증번호가 확인되지 않았습니다", Toast.LENGTH_SHORT).show()

            //비밀번호가 비어있을 때
            else if (TextUtils.isEmpty(Password.text.toString()))
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()

            //비밀번호 확인이 비어있을 때 5
            else if (TextUtils.isEmpty(Password_check.text.toString()))
                Toast.makeText(this, "비밀번호 확인을 입력해주세요", Toast.LENGTH_SHORT).show()

            //비밀번호가 같지 않을 때 4
            else if (!Password.text.toString().equals(Password_check.text.toString()))
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()


            //정보란이 입력됬을 때
            else {
                auth.createUserWithEmailAndPassword(Email.text.toString(), Password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            var user: FirebaseUser? = auth.currentUser
                            var userId: String = user!!.uid

                            databaseRef =
                                FirebaseDatabase.getInstance().getReference().child("Users").child(auth.uid!!)
                            var hashMap: HashMap<String, String> = HashMap()
                            hashMap.put("userId", userId)
                            hashMap.put("userStudentID", StudentID.text.toString())
                            hashMap.put("userName", Name.text.toString())
                            hashMap.put("userNickname", Nickname.text.toString())
                            hashMap.put("userEmail", Email.text.toString())
                            hashMap.put("userGrade",userGrade)
                            hashMap.put("reviewMax",reviewMax)
                            hashMap.put("reviewMin",reviewMin)
                            hashMap.put("reviewSum",reviewSum)
                            hashMap.put("reviewNum",reviewNum)

                            var FloatHashMap:HashMap<String,Float> = HashMap() //유저의 평점 관리 해쉬맵
                            //FloatHashMap.put("userGrade",userGrade)
                            //FloatHashMap.put("reviewMax",reviewMax)
                            //FloatHashMap.put("reviewMin",reviewMin)
                            //FloatHashMap.put("reviewSum",reviewSum)

                            var IntHashMap:HashMap<String,Int> =HashMap()
                            //IntHashMap.put("userReview",reviewNum)

                            var ListHashMap:HashMap<String,MutableList<String>> = HashMap()
                            ListHashMap.put("userMate",mateList)
                            ListHashMap.put("userReview",userReview)

                            databaseRef.setValue(hashMap,ListHashMap).addOnCompleteListener(this){
                                if(it.isSuccessful){
                                    val intent = Intent(this, SelectImageActivity::class.java)
                                    startActivity(intent)
                                    Toast.makeText(this, "가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                        }
                        else
                            Toast.makeText(this, "이미 가입된 계정이 존재합니다", Toast.LENGTH_SHORT).show()
                    }
            }
        }


        //SUBMIT 버튼 클릭시
        S_btn.setOnClickListener {
            if (TextUtils.isEmpty(Email.text.toString()))
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()

            //이렇게 변환과정을 모두 한번에 명시해야 에러가 나지 않았음
            //변환 과정을 변수마다 저장하니까 제대로 작동하지 않았음 <- 왜 이런지는 잘 모르겠음..
            else if((Email.text.toString().substring(
                    Email.text.toString().indexOf("@")+1).equals("hs.ac.kr"))){
                MailFunction.sendEmail(Email.text.toString()) // Mailsender 클래스의 sendemail 함수 호출
                Toast.makeText(this, "이메일을 전송하였습니다", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "이메일 형식이 맞지 않습니다", Toast.LENGTH_SHORT).show()

        }

        //인증 버튼 클릭시
        A_btn.setOnClickListener{
            if (TextUtils.isEmpty(Authnum.text.toString()))
                Toast.makeText(this, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show()

            else if(MailFunction.Authnumber.toString().equals(Authnum.text.toString())) {
                Toast.makeText(this, "인증번호가 확인 되었습니다", Toast.LENGTH_SHORT).show()
                check = true
            }
            else
                Toast.makeText(this, "인증번호가 맞지 않습니다", Toast.LENGTH_SHORT).show()
        }

        //뒤로가기 버튼 클릭시
        B_btn.setOnClickListener{
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
