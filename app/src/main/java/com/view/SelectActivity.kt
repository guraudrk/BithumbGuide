package com.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.background.GetCoinPriceRecentContractedWorkManager
import com.view.main.MainActivity
import com.bithumbGuide.coco.databinding.ActivitySelectBinding
import com.view.adapter.SelectRVAdapter
import java.util.concurrent.TimeUnit

class SelectActivity : AppCompatActivity() {

    //binding을 쓴다.
    private lateinit var binding : ActivitySelectBinding

    private val viewModel : SelectViewModel by viewModels()




    //어뎁터를 선언한다.
    private lateinit var selectRVAdapter: SelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SelectViewModel의 test를 실행하는 코드.

        //LiveData의 변화를 observe를 통해 감지한다!!
        //동시에, 데이터를 받아온다!
        viewModel.getCurrentCoinList()
        viewModel.currentPriceResult.observe(this, Observer{


            //위에서 리스트를 받아왔기 때문에 adapter의 안에 it을 쓸 수 있다.
            selectRVAdapter = SelectRVAdapter(this,it)

            //리사이클러뷰의 어뎁터를 연결한다.
            binding.coinListRV.adapter = selectRVAdapter
            //binding을 쓴다.

            //실제로 화면을 보이게 하는 코드이다.
            binding.coinListRV.layoutManager = LinearLayoutManager(this)

        })


        //처음 접속했다면 flag 의 true/false를 바꾸는 함수이다.


        //mainactivity로 이동
        binding.gotoMainButton.setOnClickListener{


            viewModel.setUpFirstFlag()


            //코인을 저장하는 코드.
            //근데 코인이 100만개이면 어떻게 할거야?
            //저장되는 동안 mainactivity로 넘어가는데??
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)





        }


        //뷰모델에서 저장이 완료가 된 것을 관찰한다.
        //저장이 완료가 된 것을 관찰 시 mainactivity로 이동한다.
        viewModel.save.observe(this, Observer {
            if(it.equals("done")){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)


                //가장 처음으로 우리가 저장한 코인 정보가 저장되는 시점이다.
                saveInterestCoinDataPeriodic()

            }
        })





    }

    //일정 주기마다 데이터를 업데이트 하는 코드이다.
    private fun saveInterestCoinDataPeriodic(){

        //15분마다 반복한다.
        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentContractedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()


        //여러개가 돌아가지 말고 유니크하게 하나만 실행하라는 것이다.
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentContractedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }
}