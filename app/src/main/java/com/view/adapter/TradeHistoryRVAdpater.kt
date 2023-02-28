package com.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.coco.R
import com.example.coco.dataModel.TradeHistoryResult

class TradeHistoryRVAdpater(val context : Context, val coinPriceList : List<TradeHistoryResult>) :
    RecyclerView.Adapter<TradeHistoryRVAdpater.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val transactionDate : TextView = view.findViewById(R.id.transaction_date)
        val unitsTraded : TextView = view.findViewById(R.id.units_traded)
        val price1 : TextView = view.findViewById(R.id.price)

        //코인 가격의 변동률을 보여준다.
        val total : TextView = view.findViewById(R.id.total)
        val background : CardView = view.findViewById(R.id.historyCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trade_history_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coinPriceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //holder와 리스트의 정보를 연결한다.

        holder.transactionDate.text = coinPriceList[position].coinInfo.transaction_date
        holder.unitsTraded.text = coinPriceList[position].coinInfo.units_traded
        holder.price1.text = coinPriceList[position].coinInfo.price
        holder.total.text = coinPriceList[position].coinInfo.total

        //type을 통해 샀는지 팔았는지를 판단해서 뷰의 색을 바꾼다.
        val buyorsell = coinPriceList[position].coinInfo.type

        if(buyorsell=="bid"){
            //매수일 때
            holder.background.setCardBackgroundColor(Color.parseColor("#ed2e11"))
        }
        else{
            //매도일 때
            holder.background.setCardBackgroundColor(Color.parseColor("#114fed"))
        }
    }
}