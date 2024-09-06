package com.bithumbGuide.coco.network.model

import com.bithumbGuide.coco.dataModel.RecentPriceData

data class RecentCoinPriceList (



    val status : String,
    val data : List<RecentPriceData>

    )