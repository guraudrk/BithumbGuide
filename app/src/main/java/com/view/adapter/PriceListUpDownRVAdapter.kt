package com.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coco.R
import com.example.coco.dataModel.UpDownDataSet

class PriceListUpDownRVAdapter(val context: Context, val dataSet : List<UpDownDataSet>) : RecyclerView.Adapter<PriceListUpDownRVAdapter.ViewHolder>(){



    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){


        val coinName = view.findViewById<TextView>(R.id.coinName)
        val coinPriceUpDown = view.findViewById<TextView>(R.id.coinPriceUpDown)
        val price = view.findViewById<TextView>(R.id.price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_price_change_item,parent,false)

    return ViewHolder(view)
    }

    override fun getItemCount(): Int {

    return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        //코인 이름을 넣는다.
        holder.coinName.text = dataSet[position].coinName


        //상승인지 아닌지에 따라 텍스트와 컬러를 설정한다.
         if(dataSet[position].upDownPrice.contains("-")){
             holder.coinPriceUpDown.text = "하락"
             holder.coinPriceUpDown.setTextColor(Color.parseColor("#114fed"))
         }

        else{
            holder.coinPriceUpDown.text="상승"
             holder.coinPriceUpDown.setTextColor(Color.parseColor("#ed2e11"))
         }


        //가격을 나타낸다.
        //자연수 기준으로 가격을 자르고 싶다면
        //holder.price.text = dataSet[position].upDownPrice.split(".")[0]을 써라.
        holder.price.text = dataSet[position].upDownPrice
    }
}