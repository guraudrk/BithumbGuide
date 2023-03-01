package com.example.coco.dataModel

import com.example.coco.dataModel.CurrentPrice
import com.example.coco.dataModel.RecentPriceData

data class TradeHistoryResult(

    val coinName : String,
    //데이터 파싱의 오류를 줄이기 위해 list로 감싼다.
    val coinInfo : List<RecentPriceData>
)
