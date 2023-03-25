package com.example.myapplication.matching

import com.google.firebase.database.MutableData

data class WaitUserData(
    val uid: String,
    var grade: Float,
    val rank: Int,
    val brandList: MutableList<Int>,
    val preferTable: ArrayList<MutableList<WaitUserData>> = arrayListOf(
        mutableListOf(), // 0
        mutableListOf(), // 1
        mutableListOf(), // 2
        mutableListOf()  // 3
    ),
    var fail: Int = 0
)
