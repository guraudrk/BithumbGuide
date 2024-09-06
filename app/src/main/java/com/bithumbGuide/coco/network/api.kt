package com.bithumbGuide.coco.network

import com.bithumbGuide.coco.network.model.AssetStatusList
import com.bithumbGuide.coco.network.model.CurrentPriceList
import com.bithumbGuide.coco.network.model.RecentCoinPriceList
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

    //입출금지원현황을 알기 위한 코드
    //https://apidocs.bithumb.com/reference/%EC%9E%85%EC%B6%9C%EA%B8%88-%EC%A7%80%EC%9B%90-%ED%98%84%ED%99%A9-all
    //https://api.bithumb.com/public/assetsstatus/ALL
    @GET("public/assetsstatus/ALL")
    suspend fun getAssetStatus() : AssetStatusList



}