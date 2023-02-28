package com.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.db.entity.InterestCoinEntity
import com.example.coco.dataModel.CurrentPrice
import com.example.coco.dataModel.CurrentPriceResult
import com.example.coco.dataModel.RecentPriceData
import com.example.coco.dataModel.dataStore.MyDataStore
import com.example.coco.dataModel.TradeHistoryResult
import com.example.coco.network.repository.DBRepository
import com.example.coco.network.repository.NetWorkRepository
import com.google.gson.Gson
import com.view.adapter.CoinInfoSeeRVAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


//아직은 이해가 잘 되지 않으니 강의의 PART1을 잘 보도록 하자.

class SelectViewModel : ViewModel() {


    private val netWorkRepository = NetWorkRepository()
    private val dbRepository = DBRepository()
    private lateinit var coininfoseeadapter: CoinInfoSeeRVAdapter

    //새로운 리스트를 정의한다. 이 리스트는 coininfosee에도 쓰이기 때문에 private를 지운다.
    lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    //체결 내역에 대한 리스트

    lateinit var tradeHistoryList: ArrayList<TradeHistoryResult>


    //데이터 변화를 관찰하는 LiveData
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult


    //다 저장되었는지 저장이 되지 않았는지 확인하는 코드이다.
    private val _saved = MutableLiveData<String>()
    val save: LiveData<String>
        get() = _saved

    // 체결 내역의 데이터 변화를 관찰하는 LiveData
    private val _tradeHistoryResult = MutableLiveData<List<TradeHistoryResult>>()
    val tradeHistoryResult: LiveData<List<TradeHistoryResult>>
        get() = _tradeHistoryResult


    //코루틴을 사용한다.
    fun getCurrentCoinList() = viewModelScope.launch {

        val result = netWorkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()


        //데이터를 원하는 대로 가공하는 방식이다.
        for (coin in result.data) {

            //예외를 잘 처리하기 위해 try-catch를 사용하면 된다.
            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)

                //우리가 정의한 리스트에 값을 추가한다.
                currentPriceResultList.add(currentPriceResult)
            } catch (e: java.lang.Exception) {
                Timber.d(e.toString())
            }

        }


        _currentPriceResult.value = currentPriceResultList
    }


    //체결내역에 관한 정보를 가져오는 코루틴을 정의한다.
    fun getInterestCoinPriceData(coin: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = netWorkRepository.getInterestCoinPriceData(coin)

        tradeHistoryList = ArrayList()


        //데이터를 원하는 대로 가공한다.


        //예외를 잘 처리하기 위해 try-catch를 사용하면 된다.
        try {
            val gson = Gson()
            val gsonToJson = gson.toJson(result.data)
            val gsonFromJson = gson.fromJson(gsonToJson, RecentPriceData::class.java)

            val tradeHistoryList1 = TradeHistoryResult(coin, gsonFromJson)

            //우리가 정의한 리스트에 값을 추가한다.
            tradeHistoryList.add(tradeHistoryList1)
            Timber.d("체결내역 성공")
        } catch (e: java.lang.Exception) {
            Timber.d("체결내역을 가져오는데 오류가 발생")

        }

        //최종적으로 라이브데이터에 저장한다.
        _tradeHistoryResult.value = tradeHistoryList

    }

        //1번 접속하면 false값을 true 값으로 바꾸는 함수이다.

        fun setUpFirstFlag() = viewModelScope.launch {
            MyDataStore().setupFirstData()
        }

        //입력한 코인 이름이 전체 코인 리스트에 있는지 확인시키는 함수
//    fun findDataOfCoinInfoSEE(selectedCoinList: ArrayList<String>, coinName : String) = viewModelScope.launch(Dispatchers.IO) {
//
//        //전체 코인 데이터를 가져와서 이름을 검색한다.
//
//        for(coinName in currentPriceResultList){
//            val selected  =selectedCoinList.contains(coinName.coinName)
//
//
//            //만약 이름이 있다면 이름에 관한 데이터를 보여준다.
//            if (selected){
//                coinName.coinName
//            }
//
//
//
//        }
//        //만약 for문 안에 이름이 없다면 이름을 다시 입력하라는 코드를 입력한다.
//        //Toast는 구려서 Timber로 대신한다.
//        Timber.d("알맞은 코인 이름을 영어 대문자로 적어주세요.")
//    }


        //db에 데이터 저장
        fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) =
            viewModelScope.launch(Dispatchers.IO) {

                //1.전체 코인 데이터를 가져와서
                //2. 내가 선택한 코인인지 아닌지 구분해서 포함하면 true
                for (coin in currentPriceResultList) {
                    val selected = selectedCoinList.contains(coin.coinName)

                    val interestCoinEntity = InterestCoinEntity(
                        0,
                        coin.coinName,
                        coin.coinInfo.opening_price,
                        coin.coinInfo.closing_price,
                        coin.coinInfo.min_price,
                        coin.coinInfo.max_price,
                        coin.coinInfo.units_traded,
                        coin.coinInfo.acc_trade_value,
                        coin.coinInfo.prev_closing_price,
                        coin.coinInfo.units_traded_24H,
                        coin.coinInfo.acc_trade_value_24H,
                        coin.coinInfo.fluctate_24H,
                        coin.coinInfo.fluctate_rate_24H,
                        selected
                    )

                    //3.저장
                    //실제로 넣어서 저장하는부분
                    interestCoinEntity.let {
                        dbRepository.insertInterestCoinData(it)
                    }
                }



                withContext(Dispatchers.Main) {

                    //context를 신경쓰는 위와 같은 코드를 꼭 작성해야 한다.
                    //저장이 다 끝나면 아래와 같은 코드를 작성한다.
                    _saved.value = "done"
                }


            }
    }
