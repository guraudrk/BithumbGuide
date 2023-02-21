package com.db.dao

import androidx.room.*
import com.db.entity.InterestCoinEntity
import java.util.concurrent.Flow


@Dao
interface InterestCoinDAO {

    //Dao는 쿼리문을 작성한 거라고 생각하면 좋다.


    //getAllData
    //데이터의 변경 사항을 감지하기 좋다.
    //flow는 데이터의 변경 사항을 감지하는 놈이다.
    //쿼리문 작성할 때 오류를 조심해라!!
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData() : kotlinx.coroutines.flow.Flow<List<InterestCoinEntity>>


    //Insert
    //이 부분은 더 학습이 필요하다.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)

    //update
    //사용자가 코인 데이터를 선택했다가 다시 취소할 수도 있고, 반대로 선택이 안된 것을 선택할 수도 있게 한다.
    @Update
    fun update(InterestCoinEntity:InterestCoinEntity)


    //getSelectedCoinList = 내가 관심있어한 코인 데이터를 가져오는 것이다.
    //코인1,2,3을 가지고 있다면 1,2,3에 대한 data를 가져오는 것이다.


    //전체중에 사용자가 선택한 것만 가져온다.
    @Query("SELECT * FROM interest_coin_table WHERE selected = :selected")
    fun getSelectedData(selected : Boolean = true) : List<InterestCoinEntity>
}