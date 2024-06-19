package com.example.finalapplication

data class ReviewData(
    var docId: String? = null,
    var comments: String? = null,
    var date_time: String? = null,
    var email: String? = null,
    var stars: Float = 0.0f,
    var title: String? = null,
    var contentid: String? = null
)
