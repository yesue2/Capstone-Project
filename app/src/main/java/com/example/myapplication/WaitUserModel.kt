package com.example.myapplication

data class WaitUserModel(
    var uid: String? = null,
    var grade: String? = null,
    var brands: List<String>,
    var timestamp: Long? = null
)


fun convertToWaitUserModel(list: List<MutableMap<String, Any>?>): ArrayList<WaitUserModel> {
    val waitUserModels = ArrayList<WaitUserModel>()
    for (item in list) {
        if (item is HashMap<*, *>) {
            val uid = item["uid"] as? String
            val grade = item["grade"] as? String
            val brands = item["brands"] as? ArrayList<String> ?: ArrayList()
            val timestamp = item["timestamp"] as? Long
            waitUserModels.add(WaitUserModel(uid, grade, brands, timestamp))
        }
    }
    return waitUserModels
}