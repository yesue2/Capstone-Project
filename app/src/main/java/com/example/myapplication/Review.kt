package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.activity_review.profile_image
import kotlinx.android.synthetic.main.activity_review.rating_bar
import kotlinx.android.synthetic.main.activity_review.username

class Review : AppCompatActivity()  {

    // firebase
    private lateinit var dbRef : DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var username : TextView
    private lateinit var profileImage : CircleImageView
    private lateinit var ratingBar : RatingBar

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        auth = FirebaseAuth.getInstance()
        username = findViewById(R.id.username)
        profileImage = findViewById(R.id.profile_image)
        ratingBar = findViewById(R.id.rating_bar)

        // firebase 데이터 연동
        val uid = auth.currentUser?.uid.toString()
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)

        // finish 에서 mateID 가져옴
        val mateID = intent.extras?.getString("mate_id").toString()
        // mate_id의 속성 불러오기
        val mate = FirebaseDatabase.getInstance().getReference("Users").child(mateID)

        var userName : String?
        var reviewSum = 0f
        var reviewNum = 0
        var reviewMax = 5f
        var reviewMin = 0f
        var grade = 0f

        mate.get().addOnSuccessListener{ dataSnapshot ->
            userName = dataSnapshot.child("userNickname").value.toString()
            Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/banana-8d3ab.appspot.com/o/Image%2F" +
                    "${mateID}?alt=media").circleCrop().into(profileImage)
            reviewSum = dataSnapshot.child("reviewSum").value.toString().toFloat()
            reviewNum = dataSnapshot.child("reviewNum").value.toString().toInt()
            reviewMax = dataSnapshot.child("reviewMax").value.toString().toFloat()
            reviewMin = dataSnapshot.child("reviewMin").value.toString().toFloat()

            // xml 파일에 매핑
            username.text = userName

            // rating bar
            ratingBar.setOnRatingBarChangeListener{ _, rating, _ ->
                grade = rating
            }
        }

        review_button.setOnClickListener{
            val intent = Intent(this,MainPage::class.java)

            // 별점 평점 update
            // 리뷰가 3개 이상이면 절단 평균 계산하는 방식으로 수정함.
            val newReviewNum : Int = reviewNum + 1
            val newReviewSum: Float
            val newUserGrade : Float

            when(newReviewNum){
                1 ->{ // 첫 번째 리뷰라면 min = max = grade
                    mate.child("reviewMin").setValue(grade)
                    mate.child("reviewMax").setValue(grade)
                    // 별점의 합 = grade
                    newReviewSum = grade
                    // 평점 = grade
                    newUserGrade = grade
                }
                in 2..10 ->{ // 열 번째 리뷰까지는 기존 별점과 비교하여 min, max 설정
                    if(grade<reviewMin) {mate.child("reviewMin").setValue(grade)}
                    else if(grade>reviewMax) {mate.child("reviewMax").setValue(grade)}
                    // 별점의 합 = 기존 sum + grade
                    newReviewSum = reviewSum + grade
                    // 평점 = newReviewSum/newReviewNum
                    newUserGrade = newReviewSum/newReviewNum
                }
                else ->{ // 열 번째 리뷰부터는 절단 평균으로 계산
                    if(grade<reviewMin) {
                        mate.child("reviewMin").setValue(grade)
                        reviewMin = grade
                    }
                    else if(grade>reviewMax) {
                        mate.child("reviewMax").setValue(grade)
                        reviewMax = grade
                    }
                    // 별점의 합 = 기존 sum + grade - reviewMin - reviewMax
                    newReviewSum = reviewSum + grade - reviewMax - reviewMin
                    // 평점 = newReviewSum/(newReviewNum-2)
                    newUserGrade = newReviewSum/(newReviewNum-2)
                }
            }

            mate.child("userGrade").setValue(newUserGrade);
            mate.child("reviewNum").setValue(newReviewNum);
            mate.child("reviewSum").setValue(newReviewSum)

            // 사용자 후기 update
            mate.child("userReview").push().setValue(review.text.toString());

            startActivity(intent)
        }

        // 이전으로 가기 버튼 클릭하면 마이 페이지로 이동
        to_my_page_button.setOnClickListener{
            val intent = Intent(this, MyPage::class.java)
            startActivity(intent)
        }
    }
}

