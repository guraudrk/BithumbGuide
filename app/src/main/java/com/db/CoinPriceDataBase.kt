package com.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.db.dao.InterestCoinDAO
import com.db.dao.SelectedCoinPriceDAO
import com.db.entity.DataConverters
import com.db.entity.InterestCoinEntity
import com.db.entity.SelectedCoinPriceEntity


@Database(entities = [InterestCoinEntity::class,SelectedCoinPriceEntity::class],version=3)
@TypeConverters(DataConverters::class)
abstract class CoinPriceDataBase : RoomDatabase() {


    abstract fun insertCoinDAO() : InterestCoinDAO
    abstract fun selectedCoinDAO() : SelectedCoinPriceDAO
    companion object{


        //이 부분은 공식문서에서 복붙을 해도 되는데, 그냥 칠 수도 있다.

        @Volatile
        private var INSTANCE : CoinPriceDataBase? = null


        //데이터베이스를 본격적으로 세팅하는 구문이다.
        fun getDatabase(
            context : Context
        ) : CoinPriceDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinPriceDataBase::class.java,
                "coin_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance

            }
        }

    }

}