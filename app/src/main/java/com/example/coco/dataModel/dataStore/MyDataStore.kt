package com.example.coco.dataModel.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.coco.App

class MyDataStore {

    //datastore은 flag의 on/off를 설정하기에 좋다.
    //가벼운 데이터를 key-value쌍으로 저장하기에 좋다.
    //이를 통해 처음 접속하는지 아닌지에 대해 설정할 것이다.


    //datastore 세팅


    private val context = App.context()

    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("user_pref")


    }

    private val mDataStore : DataStore<Preferences> = context.dataStore

    private val FIRST_FLAG = booleanPreferencesKey("FIRST_FLAG")


    //메인엑티비티로 갈 때 true라고 변경한다.

    //처음 접속하는 유저가 아니면 true이고
    //처음 접속하는 유저이면 false이다.


    suspend fun setupFirstData(){
        mDataStore.edit{preferences ->
            preferences[FIRST_FLAG] = true

        }
    }

    suspend fun getFirstData() : Boolean{
        var currentValue = false

        mDataStore.edit{preferences ->
            currentValue = preferences[FIRST_FLAG] ?:false
        }

        return currentValue
    }


}