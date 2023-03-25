package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityFindPasswordBinding
import com.example.myapplication.databinding.ActivitySelectImageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_find_password.*

class FindPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)


        val Useremail=find_userEmail

        ok_btn.setOnClickListener{
            Firebase.auth.sendPasswordResetEmail(Useremail.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        Toast.makeText(this,"이메일을 전송했습니다",Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this,"이메일 전송을 실패했습니다. 메일을 확인해주세요",Toast.LENGTH_SHORT).show()
                }
        }
    }
}