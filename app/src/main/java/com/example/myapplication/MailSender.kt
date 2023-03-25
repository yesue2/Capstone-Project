package com.example.myapplication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailSender : Authenticator(){
    // 보내는 사람 이메일과 비밀번호
    val Sender_Email = "team4delishare@gmail.com"
    val password = "oomltgwdvmqptjnt"
    val Authnumber:Int = Random().nextInt(99999)+10000 //인증번호 생성 변수

    // 보내는 사람 계정 체크
    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(Sender_Email, password)
    }

    // 메일 보내기
    fun sendEmail(toEmail: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val props = Properties()
            props.setProperty("mail.transport.protocol", "smtp")
            props.setProperty("mail.host", "smtp.gmail.com")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", "465")
            props.put("mail.smtp.socketFactory.port", "465")
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            props.put("mail.smtp.socketFactory.fallback", "false")
            props.setProperty("mail.smtp.quitwait", "false")

            // 구글에서 지원하는 smtp 정보를 받아와 MimeMessage 객체에 전달
            val smtp_info = Session.getDefaultInstance(props, this@MailSender)

            // 메시지 객체 만들기
            val message = MimeMessage(smtp_info)
            message.sender = InternetAddress(Sender_Email) // 보내는 사람 설정
            message.addRecipient(Message.RecipientType.TO, InternetAddress(toEmail)) // 받는 사람 설정
            message.subject = "[Delishare] 가입 인증 메일입니다" // 이메일 제목
            message.setText("안녕하세요 Delishare입니다.\n" +
                    "Delishare 회원 가입을 진심으로 환영합니다.\n" +
                    "아래의 인증번호를 입력하여 가입인증을 하시면 Delishare 회원 가입이 완료됩니다.\n" +
                    "인증번호: "+
                    Authnumber) // 이메일 내용

            // 전송
            Transport.send(message)
        }
    }
}
