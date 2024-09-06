package com.view.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.view.main.MainActivity
import com.bithumbGuide.coco.databinding.ActivityIntroBinding


//기존에는 handler를 이용해서 3초 뒤에 다른 엑티비티로 이동
//우리는 splash screen을 이용했다.

//findViewById가 아닌 ViewBinding을 사용했다.

class IntroActivity : AppCompatActivity() {



    //뷰바인딩

    private lateinit var binding : ActivityIntroBinding


    //1번 접속했느냐 마느냐의 변수를 정의하기 위한 코드를 적는다.
    private val ViewModel : IntroViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        //강의-스플래시 화면 만들기 에서 만든 것들을 적용하기 위해서는
        //아래의 installSplashScreen() 를 적어야 한다.
        installSplashScreen()


        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewModel.checkFirstFlag()


        ViewModel.first.observe(this, Observer {
            if(it){
                //처음 접속하는 유저가 아님
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

            else{
                //처음 접속하는 유저
                //처음 접속하는 유저만 처음 화면이 보이게 한다.


                //이 코드는 애니메이션에 대한 코드이다. '화면 에니매이션 만들기' 참고.
                binding.animationView.visibility = View.INVISIBLE


                binding.fragmentContainerView.visibility = View.VISIBLE
            }
        })
    }
}