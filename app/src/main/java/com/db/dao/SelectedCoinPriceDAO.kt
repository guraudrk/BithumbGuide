package com.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.db.entity.SelectedCoinPriceEntity


@Dao
interface SelectedCoinPriceDAO {

    //getAllData
    @Query("SELECT * FROM selected_coin_price_table")
    fun getAllData() : List<SelectedCoinPriceEntity>

    //insert
    //OnConflictStrategy는 충돌 시의 대처법에 대한 것이다.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)


//하나의 코인에 대해서 저장된 정보를 가져오는 친구
    //btc에 대해 현재가격이 15분,30,45분 저장 데이터와 비교해 어떻게 변했는지
    //비교하는 용도이다.
@Query("SELECT * FROM selected_coin_price_table WHERE coinName = :coinName")
fun getOneCoinData(coinName : String) : List<SelectedCoinPriceEntity>

}