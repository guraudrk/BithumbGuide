package com.example.coco.network.repository

import com.example.coco.network.RetrofitInstance
import com.example.coco.network.api

class NetWorkRepository {

    //여기에서 api를 호출을 해서 사용을 할 것이다.

    private val client  = RetrofitInstance.getInstance().create(api::class.java)

    suspend fun getCurrentCoinList() = client.getCurrentCoinList()

    suspend fun getInterestCoinPriceData(coin : String) = client.getRecentCoinPrice(coin)


    //입출금 지원에 대해 데이터를 가져와주는 함수
    suspend fun getAssetStatusData() = client.getAssetStatus()
}