package com.example.coco.network

import com.example.coco.network.model.CurrentPriceList
import com.example.coco.network.model.RecentCoinPriceList
import retrofit2.http.GET
import retrofit2.http.Path

//api를 정의하는 역활을 한다.
//class 가 아닌 interface로 정의를 한다는 것을 유의하자.
interface api {

    // https://api.bithumb.com/public/ticker/ALL_KRW
    // public/ticker/ALL_KRW
    @GET("public/ticker/ALL_KRW")
    suspend fun getCurrentCoinList() : CurrentPriceList

    // https://apidocs.bithumb.com/reference/%EC%B5%9C%EA%B7%BC-%EC%B2%B4%EA%B2%B0-%EB%82%B4%EC%97%AD
    // https://api.bithumb.com/public/transaction_history/BTC_KRW
    @GET("public/transaction_history/{coin}_KRW")
    suspend fun getRecentCoinPrice(@Path("coin") coin : String) : RecentCoinPriceList





}