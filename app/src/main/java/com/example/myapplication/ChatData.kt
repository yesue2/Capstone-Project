package com.example.myapplication

class ChatData {
    var msg:String? = ""
    var nickname:String? = ""
    var time:String? = ""

    constructor(msg:String?,nickname:String?, time:String?){
        this.msg = msg
        this.nickname = nickname
        this.time = time
    }

    override fun toString(): String {
        return nickname + "," + msg + "," + time
    }
}