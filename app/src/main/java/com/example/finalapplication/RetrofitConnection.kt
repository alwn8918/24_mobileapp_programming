package com.example.finalapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//https://apis.data.go.kr/B551011/KorWithService1/areaBasedList1?serviceKey=6u8TrayNZB5SJTA16jp8QA%2BGxqnfg0%2BiIQ3p9BpNTvNTJglB%2BfxHVqibODUo1xV91MI3tJhn6d670Om3L6gGag%3D%3D&areaCode=35&listYN=Y&arrange=A&MobileOS=ETC&MobileApp=AppTest&_type=json
class RetrofitConnection {
    companion object {
        private const val BASE_URL = "https://apis.data.go.kr/B551011/KorWithService1/"

        val listNetServ: NetworkService
        val listRetrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        init {
            listNetServ = listRetrofit.create(NetworkService::class.java)
        }
    }
}