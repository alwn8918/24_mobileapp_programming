package com.example.finalapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://apis.data.go.kr/B551011/KorWithService1/areaBasedList1?serviceKey=6u8TrayNZB5SJTA16jp8QA%2BGxqnfg0%2BiIQ3p9BpNTvNTJglB%2BfxHVqibODUo1xV91MI3tJhn6d670Om3L6gGag%3D%3D&areaCode=35&listYN=Y&arrange=A&MobileOS=ETC&MobileApp=AppTest&_type=json
interface NetworkService {
    @GET("areaBasedList1")
    fun getJsonList(
        @Query("serviceKey") serviceKey: String,
        @Query("areaCode") areaCode: Int,
        @Query("listYN") listYN: String,
        @Query("arrange") arrange: String,
        @Query("MobileOS") MobileOS: String,
        @Query("MobileApp") MobileApp: String,
        @Query("_type") _type: String
    ): Call<ListResponse>
}