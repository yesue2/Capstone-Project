package com.example.myapplication

class UserModel {
    var nickname:String? = ""
    var uid:String? = ""

    constructor()  // 기본 생성자

    constructor(uid:String?,nickname:String?){
        this.uid = uid
        this.nickname = nickname
    }
}