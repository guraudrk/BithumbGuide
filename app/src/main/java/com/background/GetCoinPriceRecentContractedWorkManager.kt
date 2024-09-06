package com.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.db.entity.SelectedCoinPriceEntity
import com.bithumbGuide.coco.network.model.RecentCoinPriceList
import com.bithumbGuide.coco.network.repository.DBRepository
import com.bithumbGuide.coco.network.repository.NetWorkRepository
import java.util.Calendar


//최근 거래된 코인 가격 내역을 가져오는 workmanager

//1.우리가 관심있어하는 코인 리스트를 가져와서
//2.관심있어하는 코인 각각의 가격 변동 정보를 가져와서(new api)
//3.관심있는 코인 각각의 가격 변동 정보를 db에 저장한다.

private val dbRepository = DBRepository()
private val netWorkRepository = NetWorkRepository()

class GetCoinPriceRecentContractedWorkManager(val context : Context,workerParameters: WorkerParameters) :CoroutineWorker(context,workerParameters){
    override suspend fun doWork(): Result {
        getAllInterestSelectedCoinData()
        return Result.success()
    }

    //a.전체 코인 데이터를 가져옴
    val typedcoin = dbRepository.getAllInterestCoinData()

    //b.타이핑한 코인이 전체 코인 데이터 안에 있는지 확인



//1.우리가 관심있어하는 코인 리스트를 가져와서
suspend fun getAllInterestSelectedCoinData(){


    //흥미 있는 코인들
    val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()



    //전체 코인들


    val timeStamp = Calendar.getInstance().time

    //반복문을 통해서 1개씩 가져온다.

    for (coinData in selectedCoinList)
    {
        //2.관심있어하는 코인 각각의 가격 변동 정보를 가져와서(new api)

       val recentCoinPriceList =  netWorkRepository.getInterestCoinPriceData(coinData.coin_name)

        saveSelectedCoinPrice(
            coinData.coin_name,
            recentCoinPriceList,
            timeStamp

        )
    }
}
//3.관심있는 코인 각각의 가격 변동 정보를 db에 저장한다.
    //그러기 위해서는 entity를 만들어야 한다.

    fun saveSelectedCoinPrice(
        coinName : String,
        recentCoinPriceList : RecentCoinPriceList,
        timeStamp : java.util.Date

        ){

        //디비에 저장을 해준다.
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStamp


        )
        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)
    }


}