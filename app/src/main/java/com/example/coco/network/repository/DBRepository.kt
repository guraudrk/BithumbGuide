package com.example.coco.network.repository

import com.db.CoinPriceDataBase
import com.db.entity.InterestCoinEntity
import com.db.entity.SelectedCoinPriceEntity
import com.example.coco.App


//왜 레포지토리를 사용하는지에 대해서는 강의 22강(로컬 데이터베이스 세팅) 참조
class DBRepository {


    val context = App.context()

    val db = CoinPriceDataBase.getDatabase(context)


   //InsertCoin

    //전체 코인 데이터 가져오기

    fun getAllInterestCoinData() = db.insertCoinDAO().getAllData()


    //코인 데이터 넣기
    fun insertInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.insertCoinDAO().insert(interestCoinEntity)

    //코인 데이터 업데이트
    fun updateIntersetCoinData(interestCoinEntity: InterestCoinEntity) = db.insertCoinDAO().update(interestCoinEntity)

    //사용자가 관심있어한 코인만 가져오기
    fun getAllInterestSelectedCoinData() = db.insertCoinDAO().getSelectedData()


    //CoinPrice에 관한 함수들
    fun getAllCoinPriceData() = db.selectedCoinDAO().getAllData()

    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) = db.selectedCoinDAO().insert(selectedCoinPriceEntity)

    fun getOneSelectCoinData(coinName : String) = db.selectedCoinDAO().getOneCoinData(coinName)
}