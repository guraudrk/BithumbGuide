package com.example.coco.dataModel

//api에 있던 그대로 적는다.

data class RecentPriceData (


    //최근 체결 내역을 알아보기 위한 data class이다.

    val transaction_date : String,
    val type : String,
    val units_traded : String,
    val price : String,
    val total : String
)
