package com.example.coco.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    //빗썸 api를 쓰기 위한 코드이다.
    //위에 class가 아닌 object를 쓰는 것에 유의하라.

    private const val BASE_URL = "https://api.bithumb.com/"

    private val client = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstance() : Retrofit{
        return client
    }

}