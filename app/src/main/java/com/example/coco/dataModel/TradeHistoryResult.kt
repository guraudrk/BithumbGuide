package com.example.coco.dataModel

import com.example.coco.dataModel.CurrentPrice
import com.example.coco.dataModel.RecentPriceData

data class TradeHistoryResult(

    val coinName : String,
    val coinInfo : RecentPriceData
)
