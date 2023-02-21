package com.view.main.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.coco.databinding.ActivitySettingBinding
import com.example.coco.service.PriceForegroundService

class SettingActivity : AppCompatActivity() {


    private lateinit var binding : ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //버튼을 각각 눌렀을 때 어떻게 동작하는지에 관해!

        binding.startForeground.setOnClickListener{


            //아래와 같이 start 혹은 stop을 보낸다.
            Toast.makeText(this, "start", Toast.LENGTH_LONG).show()
            val intent = Intent(this,PriceForegroundService::class.java)
            intent.action = "START"
            //service의 경우,startactivity가 아니라 startService이다.
            startService(intent)

        }

        binding.stopForeground.setOnClickListener {

            Toast.makeText(this, "stop", Toast.LENGTH_LONG).show()
            val intent = Intent(this,PriceForegroundService::class.java)
            intent.action = "STOP"
            startService(intent)

        }

    }
}