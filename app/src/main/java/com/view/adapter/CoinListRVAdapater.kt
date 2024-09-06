package com.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.db.entity.InterestCoinEntity
import com.bithumbGuide.coco.R

class CoinListRVAdapater (val context: Context,val dataSet : List<InterestCoinEntity>) : RecyclerView.Adapter<CoinListRVAdapater.ViewHolder>(){

    interface ItemClick {
        fun onClick(view : View, position: Int)
    }
    var itemClick : ItemClick? = null

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val coinName = view.findViewById<TextView>(R.id.coinName)
        val likeBtn = view.findViewById<ImageView>(R.id.likeBtn)

        //변동률에 대한 변수이다.
        val pricerate = view.findViewById<TextView>(R.id.maincoinitemupdownrate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_coin_item, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.findViewById<ImageView>(R.id.likeBtn).setOnClickListener { v ->
            itemClick?.onClick(v, position)
        }

        holder.coinName.text = dataSet[position].coin_name
        //여기서도 변동률을 보여준다.
        holder.pricerate.text = dataSet[position].fluctate_rate_24H

        //24시간 변동률을 변수로 받아온다.
        val fluctate_24H = dataSet[position].fluctate_rate_24H


        //24시간 변동률에 따라 텍스트 색을 바꾼다.
        if(fluctate_24H.contains("-")){


            holder.pricerate.setTextColor(Color.parseColor("#114fed"))
        }
        else{

            holder.pricerate.setTextColor(Color.parseColor("#ed2e11"))
        }

        val selected = dataSet[position].selected
        if(selected) {
            holder.likeBtn.setImageResource(R.drawable.star_selected)
        } else {
            holder.likeBtn.setImageResource(R.drawable.star_unselected)
        }


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}