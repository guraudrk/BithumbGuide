package com.example.coco.dataModel

//api에 있던 그대로 적는다.

data class RecentPriceData (
    val transaction_date : String,
    val type : String,
    val units_traded : String,
    val price : String,
    val total : String

        )