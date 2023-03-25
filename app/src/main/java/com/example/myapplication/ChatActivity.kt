package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Rect
import android.view.View

class ChatActivity : AppCompatActivity() {
    private val adapter = CharAdapter()
    lateinit var nickname: String
    lateinit var chatNum: String
    lateinit var myRef: DatabaseReference
    private var isOpen = false // 키보드 올라왔는지 확인
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var database = FirebaseDatabase.getInstance()
        var auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()

        //가져오기 전에
        //채팅방 번호랑, 이름만 넘겨주시고
        //채팅방 번호의 UsersID 이름이랑, uid 넣어주시면 됩니다.

        //이름 채팅방 설정
        //이거는 매칭 화면에서 정보를 넣어주면 됨
        nickname = intent.getStringExtra("nickname").toString()
        chatNum = intent.getStringExtra("teamid").toString()

//        myRef = database.getReference("User").child(uid).child("userNickname")
//        myRef.get().addOnSuccessListener{
//            nickname = it.value.toString()
//        }

        //hash map에 매칭된 사용자들, (이름, uid) 넝어주기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                dataSnapshot.children.forEach {
                    it.children.forEach {
                        println(adapter.UsersIDMap)
                        adapter.UsersIDMap.put(it.key!!, it.value!!)
                        chat_recyclerView.adapter = adapter
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        var tempMap = HashMap<String, String>()
        tempMap.put(nickname, uid)
        myRef = database.getReference("message").child(chatNum).child("UsersID")
        myRef.child(uid).setValue(tempMap)
        myRef.addValueEventListener(postListener)

        myRef = database.getReference("message").child(chatNum).child("contents")
        //database.getReference("message").child(chatNum).child("images").child("nick1").setValue("~~.png")

        //입력 했을 때
        chat_submit_button.setOnClickListener {
            addChat(chat_inputBox.text.toString(),nickname)
            chat_inputBox.setText("")
        }

        //나가기 버터느
        chat_quit_button.setOnClickListener {
            database.getReference("message").child(chatNum).removeValue()
            val intent = Intent(this, FinishActivity::class.java)
            intent.putExtra("userMap", adapter.UsersIDMap);
            startActivity(intent)
            finish()
        }

        //여기서 작성했던 채팅 목록들 가져옴
        myRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var dataHash = snapshot.getValue() as HashMap<String, String>
                adapter.itemList.add(ChatData(dataHash["msg"], dataHash["nickname"], dataHash["time"]))
                chat_recyclerView.adapter = adapter
                chat_recyclerView.scrollToPosition(adapter.itemCount-1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                var dataHash = snapshot.getValue() as HashMap<String, String>
                //adapter.itemList.clear()
                //chat_recyclerView.adapter = adapter
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        //adapter 설정
        adapter.context = this
        adapter.myNickname = nickname
        //adapter.itemList.add(ChatData("안녕!", "nick1"))
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        chat_recyclerView.layoutManager = layoutManager
        chat_recyclerView.adapter = adapter


        //시간 설정
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")
        val formatted = current.format(formatter)

        chat_date_textView.text = formatted
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addChat(msg:String?, nickname:String?){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        val formatted = current.format(formatter)
        var chatdata:ChatData = ChatData(msg, nickname, formatted)
         chat_recyclerView.scrollToPosition(adapter.itemCount-1)

        if (msg != null && !msg.equals("")){
            myRef.child(adapter.itemList.size.toString()).setValue(chatdata)
        }
    }
     // 키보드 Open/Close 체크
    private fun setupView() {
        CHATTING.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            CHATTING.getWindowVisibleDisplayFrame(rect)

            val rootViewHeight = CHATTING.rootView.height
            val heightDiff = rootViewHeight - rect.height()
            isOpen = heightDiff > rootViewHeight * 0.25 // true == 키보드 올라감
        }
    }

    /*** 세로 스크롤 가능 여부 확인 ***/
    fun RecyclerView.isScrollable(): Boolean {
        return canScrollVertically(1) || canScrollVertically(-1)
    }

    /*** StackFromEnd 설정 ***/
    fun RecyclerView.setStackFromEnd() {
        (layoutManager as? LinearLayoutManager)?.stackFromEnd = true
    }

    /*** StackFromEnd 확인***/
    fun RecyclerView.getStackFromEnd(): Boolean {
        return (layoutManager as? LinearLayoutManager)?.stackFromEnd ?: false
    }

    private val onLayoutChangeListener =
        View.OnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            // 키보드가 올라와 높이가 변함
            if (bottom < oldBottom) {
                CHATTING.scrollBy(0, oldBottom - bottom) // 스크롤 유지를 위해 추가
            }
        }
}
