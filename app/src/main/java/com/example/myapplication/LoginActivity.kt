package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var firebaseUser:FirebaseUser

    private lateinit var btn_login:Button
    private lateinit var btn_signup:Button
    private lateinit var btn_findpwd:Button

    private lateinit var email:EditText
    private lateinit var pwd:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth= FirebaseAuth.getInstance()

        btn_login=findViewById(R.id.login_btn)
        email=findViewById(R.id.login_email)
        pwd=findViewById(R.id.login_password)
        btn_signup=findViewById(R.id.sign_btn)
        btn_findpwd=findViewById(R.id.find_pwd_btn)

        btn_login.setOnClickListener{
            val Email = email.text.toString()
            val Password=pwd.text.toString()

            if(TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password)){
                Toast.makeText(this,"이메일 또는 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else{
                //성공했을 시
                auth!!.signInWithEmailAndPassword(Email,Password).
                addOnCompleteListener(this){
                    if(it.isSuccessful){
                        email.setText("")
                        pwd.setText("")
                        val intent = Intent(this,MainPage::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,
                            "이메일 또는 비밀번호가 유효하지 않습니다",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btn_signup.setOnClickListener{
            val intent=Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_findpwd.setOnClickListener{
            val intent=Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }
    }
    //로그인 자동
    override fun onStart() {
        super.onStart()
        if(auth?.currentUser != null) {
            startActivity(Intent(this, MainPage::class.java))
            finish()
        }
    }
}