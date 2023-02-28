package com.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.db.entity.InterestCoinEntity
import com.example.coco.dataModel.UpDownDataSet
import com.example.coco.network.repository.DBRepository
import com.example.coco.network.repository.NetWorkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel :ViewModel() {


    //db에서 데이터를 가져온다.
    private val dbRepository =DBRepository()
    private val NetWorkRepository = NetWorkRepository()

    //받아온 데이터를 넣어주는 부분이다.
    lateinit var selectedCoinList : LiveData<List<InterestCoinEntity>>



    //15,30,45분전,1시간 전에 대한 livedata를 마련한다.
    private val _arr15min = MutableLiveData<List<UpDownDataSet>>()
    val arr15min : LiveData<List<UpDownDataSet>>
    get() = _arr15min

    private val _arr30min = MutableLiveData<List<UpDownDataSet>>()
    val arr30min : LiveData<List<UpDownDataSet>>
        get() = _arr30min

    private val _arr45min = MutableLiveData<List<UpDownDataSet>>()
    val arr45min : LiveData<List<UpDownDataSet>>
        get() = _arr45min

    private val _arr1hour = MutableLiveData<List<UpDownDataSet>>()
    val arr1hour : LiveData<List<UpDownDataSet>>
    get() = _arr1hour



    fun getAllInterestCoinData() = viewModelScope.launch{

        val coinList  = dbRepository.getAllInterestCoinData().asLiveData()
        //받아온 데이터를 넣어준다.

        selectedCoinList = coinList

    }


    //코인 정보가 업데이트 될 때 마다 observe를 하는 코드이다.
    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity)=
        CoroutineScope(Dispatchers.IO).launch{


        //selected가 false이면 true로 변경! 그 반대도 마찬가지지
       if(interestCoinEntity.selected){
            interestCoinEntity.selected = false
        }
        else{

            interestCoinEntity.selected = true
        }

        //위에서 변경된 정보를 디비에 저장해야 한다.
        //변경된 정보를 디비에 저장하는 놈이다.
        dbRepository.updateIntersetCoinData(interestCoinEntity)
    }







    //PriceChangeFragment
    //1.우리가 관심있다고 선택한 코인 리스트를 가져옴
    //2.관심있다고 선택한 코인 리스트를 반복분을 통해 하나씩 가져옴
    //3.저장된 코인 가격 리스트를 가져옴
    //4.시간대마다 어떻게 변경되었는지를 알려주는 로직을 작성

    fun getAllSelectedCoinData() = viewModelScope.launch(Dispatchers.IO){


        //1.우리가 관심있다고 선택한 코인 리스트를 가져옴
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()


        //15,30,45분마다 어떻게 바뀌는지 배열을 다 정의한다.

        val arr15min = ArrayList<UpDownDataSet>()
        val arr30min = ArrayList<UpDownDataSet>()
        val arr45min = ArrayList<UpDownDataSet>()
        val arr1hour = ArrayList<UpDownDataSet>()

        //2.관심있다고 선택한 코인 리스트를 반복분을 통해 하나씩 가져옴

        for(data in selectedCoinList){

            val coinName = data.coin_name

            //3.저장된 코인 가격 리스트를 가져옴
            //비트코인이라면, 비트코인에 대한 정보를 막 가져오는 것이다.
            val oneCoinData = dbRepository.getOneSelectCoinData(coinName).reversed() //[15분전,30분전,45분전.....]
            //가장 마지막에 저장된 값이 최신값일 것이다. 그러므로 거꾸로 가져온다.



            val size = oneCoinData.size

            if(size>1) //디비에 값이 2개 이상은 있다.
            {
                //현재와 15분전의 값을 비교하기 위해 2개 이상은 있어야 한다.

                //변동률을 구하기 위한 식이다.
                val changedPrice = ((oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble())/oneCoinData[1].price.toDouble())*100
                //값을 구하면, 이를 string으로 바꿔준 뒤, 소수 둘째자리까지 보여준다.
                val toUpDownDataSet = String.format("%.2f",changedPrice)
                val UpDownDataSet = UpDownDataSet(
                    coinName,
                    toUpDownDataSet
                )
                //기존에 사용했던 코드
//                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
//                val UpDownDataSet = UpDownDataSet(
//                    coinName,
//                    changedPrice.toString()
//                )
                //반복문이 나올때마다 업데이트를 해준다.

                arr15min.add(UpDownDataSet)
            }

            if(size>2)
            {

                //현재와 30분전의 값을 비교하기 위해 3개 이상은 있어야 한다.
                val changedPrice = ((oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble())/oneCoinData[2].price.toDouble())*100
                //값을 구하면, 이를 string으로 바꿔준 뒤, 소수 둘째자리까지 보여준다.
                val toUpDownDataSet = String.format("%.2f",changedPrice)
                val UpDownDataSet = UpDownDataSet(
                    coinName,
                    toUpDownDataSet
                )
                //반복문이 나올때마다 업데이트를 해준다.

                arr30min.add(UpDownDataSet)

            }

            if(size>3)
            {
                //현재와 45분전의 값을 비교하기 위해 4개 이상은 있어야 한다.
                val changedPrice = ((oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble())/oneCoinData[3].price.toDouble())*100
                //값을 구하면, 이를 string으로 바꿔준 뒤, 소수 둘째자리까지 보여준다.
                val toUpDownDataSet = String.format("%.2f",changedPrice)
                val UpDownDataSet = UpDownDataSet(
                    coinName,
                    toUpDownDataSet
                )

                arr45min.add(UpDownDataSet)
            }
            if(size>4)
            {
                //현재와 1시간전의 값을 비교하기 위해 4개 이상은 있어야 한다.
                val changedPrice = ((oneCoinData[0].price.toDouble() - oneCoinData[4].price.toDouble())/oneCoinData[4].price.toDouble())*100
                //값을 구하면, 이를 string으로 바꿔준 뒤, 소수 둘째자리까지 보여준다.
                val toUpDownDataSet = String.format("%.2f",changedPrice)
                val UpDownDataSet = UpDownDataSet(
                    coinName,
                    toUpDownDataSet
                )

                arr1hour.add(UpDownDataSet)
            }

        }


        //스레드를 제어하기 위해 쓰는 코드
        //스레드 관련 오류가 뜨면 이 코드를 써보자.
        withContext(Dispatchers.Main) {
    //반복문이 다 끝나면 데이터를 실질적으로 넣어준다.
            _arr15min.value = arr15min
            _arr30min.value = arr30min
            _arr45min.value = arr45min
            _arr1hour.value = arr1hour
}





    }
}