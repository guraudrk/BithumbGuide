package com.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coco.R
import com.example.coco.dataModel.CurrentPriceResult

class SelectRVAdapter(val context : Context, val coinPriceList : List<CurrentPriceResult>)
    :RecyclerView.Adapter<SelectRVAdapter.ViewHolder>(){


    //like한 코인의 리스트이다.
    val selectedCoinList = ArrayList<String>()

        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

            val coinName : TextView = view.findViewById(R.id.coinName)
            val coinPriceUpDown : TextView = view.findViewById(R.id.coinPriceUpDown)
            val likeImage : ImageView = view.findViewById(R.id.likeBtn)

            //코인 가격의 변동률을 보여준다.
            val pricechange : TextView = view.findViewById(R.id.PriceUpDown)
        }


    //데이터를 실제로 바인딩하는 코드이다.
    //제작공정이라고 생각하면 편하다.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.coinName.text = coinPriceList[position].coinName

        val fluctate_24H = coinPriceList[position].coinInfo.fluctate_24H

        //변동률을 나타내주는 코드이다.
        holder.pricechange.text = coinPriceList[position].coinInfo.fluctate_rate_24H


        //해당 부분에 마이너스가 들어있으면 하락이라고 뜨게 한다!
        //텍스트의 컬러를 바꾸는 것은 덤이다.
        if(fluctate_24H.contains("-")){
            holder.coinPriceUpDown.text = "하락입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#114fed"))
            holder.pricechange.setTextColor(Color.parseColor("#114fed"))
        }

        else{
            holder.coinPriceUpDown.text="상승입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#ed2e11"))
            holder.pricechange.setTextColor(Color.parseColor("#ed2e11"))
        }


        //like 이미지를 누르면 색깔이 빨간색으로 바뀌는 부분이다.
        val likeImage = holder.likeImage

        //현재 클릭한 코인에 대한 코드
        val currentCoin = coinPriceList[position].coinName





        //리사이클러뷰의 중복을 막기 위한 코드이다.
        if(selectedCoinList.contains(currentCoin)){

            likeImage.setImageResource(R.drawable.star_selected)
        }else{
            likeImage.setImageResource(R.drawable.star_unselected)

        }


        //이미지를 클릭하면 그에 따른 변화에 대한 코드이다.
        likeImage.setOnClickListener{


            //이미 like한 놈을 해지
            if(selectedCoinList.contains(currentCoin)){
                selectedCoinList.remove(currentCoin)
                likeImage.setImageResource(R.drawable.star_unselected)
            }

            else
            {
                selectedCoinList.add(currentCoin)
                likeImage.setImageResource(R.drawable.star_selected)
            }


           }
    }


    //레이아웃 파일을 보여주도록 하는 부분이다. 실제로 보여주는 부분이라고 생각하면 된다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item,parent,false)
    return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return coinPriceList.size
    }
}