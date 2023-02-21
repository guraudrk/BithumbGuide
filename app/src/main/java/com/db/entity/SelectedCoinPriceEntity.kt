package com.db.entity

import android.provider.ContactsContract.Data
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.sql.Date
import java.sql.Time


@Entity(tableName = "selected_coin_price_table")
data class SelectedCoinPriceEntity (


        @PrimaryKey(autoGenerate = true)
        val id : Int,
        val coinName : String,
        val transaction_date : String,
        val type : String,
        val units_traded : String,
        val price : String,
        val total : String,
        val timeStamp : java.util.Date
        //timeStamp는 date를 언제 저장했는지에 관한 변수이므로 data로 한다.


        )


//date는 그대로 저장하면 에러가 나기 때문에, 새롭게 저장해준다.

class DataConverters{
        @TypeConverter
        fun fromTimestamp(value : Long) : java.util.Date{
                return java.util.Date(value)
        }

        @TypeConverter
        fun dateToTimestamp(date : java.util.Date) : Long{
                return date.time
        }
}