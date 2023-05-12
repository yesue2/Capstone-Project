package com.example.myapplication

data class WaitUserModel(
    val uid: String,
    var grade: Float,
    val rank: Int,
    val brandList: MutableList<Int>,
    val preferTable: ArrayList<MutableList<WaitUserModel>> = arrayListOf(
        mutableListOf(), // 0
        mutableListOf(), // 1
        mutableListOf(), // 2
        mutableListOf()  // 3
    ),
    var fail: Int = 0
)