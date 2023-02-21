package com.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.coco.R
import com.example.coco.databinding.ActivityMainBinding
import com.view.main.setting.SettingActivity

class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //바인딩을 통해 setcontentview를 새롭게 정의했다.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //메인 화면의 밑의 두 부분을 왔다갔다 할 수 있게 하는 코드이다.
        val bottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragmentContainerView1)

        bottomNavigationView.setupWithNavController(navController)



        //설정 버튼을 누르면 설정 버튼으로 이동한다.
        binding.setting.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }




    }
}