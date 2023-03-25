package com.example.myapplication.matching

import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
fun main() {
    var userList: MutableList<WaitUserData> = mutableListOf()
    var mateList = mutableListOf<MateData>()
    val gradeMin: Float = 0f
    val gradeMax: Float = 5f
    var ranGrade = 0f
    var ranBrandNum: Int
    var ranBrandList: MutableList<Int> = mutableListOf()

    var brandList : MutableList<Int> = mutableListOf()
    for(index in 1..123){
        brandList.add(index-1,index)
    }

    for(userNum in 10..1000 step 10){
        print("\n$userNum")
        userList = mutableListOf()
        mateList = mutableListOf()
        for (j in 0 until userNum) {
            //ranBrandNum = Random.nextInt(1000) // 1/5꼴로 상관 없음 사용자.
            ranGrade = gradeMin + Random.nextFloat() * (gradeMax - gradeMin)
            brandList.shuffle()
            //if (ranBrandNum == 0) { // 상관 없음
                //userList.add(WaitUserData("", ranGrade, j, mutableListOf(0)))
            //} else {
                ranBrandNum = Random.nextInt(3)+1 // 1,2,3
                ranBrandList = mutableListOf()
                for (i in 0 until ranBrandNum) { // 선호하는 브랜드가 1개, 2개, 3개
                    ranBrandList.add(i, brandList[i])
                }
                userList.add(WaitUserData("", ranGrade, j, ranBrandList))
            //}
        }

        val measuredTime = measureTimedValue {
            // input code
            // 정렬
            userList.sortWith(compareBy<WaitUserData> { it.grade }.thenBy { it.rank })
            userList.reverse()
        }
        print(" ${measuredTime.duration.inWholeMicroseconds}")

        var sameNum: Int
        val measuredTime1 = measureTimedValue {
            // 선호도 테이블 생성
            for (i in 0 until userNum - 1) {
                for (j in i + 1 until userNum) { // 자기 자신 다음 user 부터 마지막 user 까지 비교
                    sameNum = 0
                    for (a in userList[i].brandList) {
                        if (a == 0) {
                            sameNum = 3
                        } else {
                            for (b in userList[j].brandList) {
                                if (b == 0) {
                                    sameNum = 3
                                } else if (b == a) {
                                    sameNum++
                                }
                            }
                        }
                    }
                    userList[i].preferTable[sameNum].add(userList[j])
                    userList[j].preferTable[sameNum].add(userList[i])
                }
            }
        }
        print(" ${measuredTime1.duration.inWholeMicroseconds}")

        var finishList: MutableList<WaitUserData> = mutableListOf()
        var mate = MateData(" ", mutableListOf())
        var success: Int
        val measuredTime2 = measureTimedValue {
            // 선택
            for (user in userList) {
                mate = MateData(" ", mutableListOf())
                if (finishList.contains(user)) {
                    continue
                }
                success = 0
                mate.userList.add(user)
                finishList.add(user)
                for (i in 3 downTo 1) { // 겹치는 개수가 3개->2개->1개
                    for (j in user.preferTable[i]) {
                        if (success >= 2) { // 2명까지만 choice
                            break
                        } else if (!finishList.contains(j)) {
                            if(i==1 && success>=1){
                                break
                            }
                            mate.userList.add(j)
                            success++
                            finishList.add(j)
                        }
                    }
                    if (success > 0) {
                        break
                    }
                }
                if(success <=0 ){ // 매칭 실패자라면
                    user.fail++
                    continue
                }
                mate.id = mate.userList[0].uid
                mateList.add(mate)
            }
        }
        print(" ${measuredTime2.duration.inWholeMicroseconds}")

        var failUserNum = 0
        for(user in userList){
            if(user.fail>0){
                failUserNum++
            }
        }

        print(" ${measuredTime.duration.inWholeMicroseconds+measuredTime1.duration.inWholeMicroseconds+measuredTime2.duration.inWholeMicroseconds}")
        var failPro : Float = failUserNum / userNum.toFloat() * 100
        print(" ${100-failPro}%")

        // print
        if(userNum ==1000 || userNum == 10){
            println()
            mateList.forEach { mate ->
                print("userList=[ ")
                mate.userList.forEach {
                    print(it.brandList)
                }
                println(" ]")
            }
            println()
        }
    }
}