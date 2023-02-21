package com.example.coco

import android.app.Application
import android.content.Context
import timber.log.Timber

class App :Application() {


    //init 과 companion object를 설정한다.
    //이에 관한 것은 강의 17강을 참고(처음 접속하는지 아닌지 flag 값 세팅 준비)
    init{

        instance = this
    }

    companion object {
        private var instance: App? = null


        fun context(): Context {
            return instance!!.applicationContext
        }
    }

    //Timber는 오류를 잡아줄 수 있는 로그를 적는 놈이다.
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}