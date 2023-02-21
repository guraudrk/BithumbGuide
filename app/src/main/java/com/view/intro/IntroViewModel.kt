package com.view.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coco.dataModel.dataStore.MyDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class IntroViewModel : ViewModel() {


    //livedata의 형태를 잘 보도록 하자.
    private val _first = MutableLiveData<Boolean>()
    val first : LiveData<Boolean>
    get()=_first


    fun checkFirstFlag() = viewModelScope.launch{

        delay(2000)

        //처음 접속의 여부를 확인한다.
        val getData = MyDataStore().getFirstData()

        _first.value = getData

        Timber.d(getData.toString())
    }
}