package com.view.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bithumbGuide.coco.dataModel.CurrentPriceResult


//CoinInfoSee의 데이터를 가공해주는 adpater이다.

class CoinInfoSeeRVAdapter(val context : Context, val coinPriceList : List<CurrentPriceResult>)
    : RecyclerView.Adapter<SelectRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectRVAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SelectRVAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}