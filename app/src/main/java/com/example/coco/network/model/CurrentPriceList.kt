package com.example.coco.network.model

data class CurrentPriceList (

    //데이터 타입을 가공하기 위한 작업이다.

    val status : String,
    val data : Map<String, Any>

        )