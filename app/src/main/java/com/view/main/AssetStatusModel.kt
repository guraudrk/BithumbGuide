package com.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coco.dataModel.AssetStatus
import com.example.coco.dataModel.AssetStatusResult
import com.example.coco.dataModel.CurrentPriceResult
import com.example.coco.network.model.AssetStatusList
import com.example.coco.network.repository.NetWorkRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

class AssetStatusModel : ViewModel() {

    //자산 지원 상태를 받아오는 레러지토리를 정의한다.
    private val netWorkRepository = NetWorkRepository()


    //1.api를 받아온다.
    //2.받아오는 것에 대한 데이터 구조를 정의한다. 그 뒤, 데이터를 가공한다.
    //3.받아온 것을 리스트에 저장한다.
    //3.5. LiveData를 통해 데이터가 변한 것을 감지한다.
    //4.for문을 통해 코인이 있는지 없는지 확인하고, 있다면 입출금 형식을 정해준다.
    //5.입출금 가능 여부에 따라 텍스트 색을 바꾼다.(if)


    //3.리스트에 저장할 변수를 정의한다.
    lateinit var AssetStatusResultList : java.util.ArrayList<AssetStatusResult>


    //3.5. LiveData를 통해 데이터가 변한 것을 감지한다.
    private val _AssetValueResult = MutableLiveData<List<AssetStatusResult>>()
    val AssetValueResult : LiveData<List<AssetStatusResult>>
    get() = _AssetValueResult

    //1.api를 받아온다.

    fun getAssetStatusList() = viewModelScope.launch {

        //입출금 현황 데이터를 result에 담는다.
        val result = netWorkRepository.getAssetStatusData()

        //배열을 새로 정의해야 데이터 변동에 따라 데이터를 변화시킬 수 있다.
        AssetStatusResultList = ArrayList()


        //2.받아오는 것에 대한 데이터 구조를 정의한다.
        for (coin in result.data){

           try{
               //2.데이터를 가공한다.
               val gson = Gson()
               val gsonToJson = gson.toJson(result.data.get(coin.key))
               val gsonFromJson = gson.fromJson(gsonToJson,AssetStatus::class.java)

               val AssetStatus = AssetStatusResult(coin.key,gsonFromJson)
               //3.리스트 안에 저장한다.
               AssetStatusResultList.add(AssetStatus)
           }
           catch(e : java.lang.Exception){
               Timber.d("입출금 목록을 불러오는 데에 실패했습니다.")
           }








        }


        //반복문이 끝나면, livedata에 값을 넣는다.
        _AssetValueResult.value = AssetStatusResultList

    }

}