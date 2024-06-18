package com.example.finalapplication

import retrofit2.Response

data class myJsonItem(val title: String, val addr1: String, val cat1: String, val cat2: String, val cat3: String, val firstImage: String)
data class myJsonItems(val item: MutableList<myJsonItem>)
data class myJsonBody(val items: myJsonItems)
data class myJsonResponse(val body: myJsonBody)
data class ListResponse(val response: myJsonResponse)
