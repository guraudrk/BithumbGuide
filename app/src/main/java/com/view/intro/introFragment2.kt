package com.view.intro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coco.databinding.FragmentIntro2Binding
import com.view.SelectActivity

class introFragment2 : Fragment() {

    //fragment에서 바인딩을 할 수 있게 하는 코드이다.
    //우리가 알던 바인딩 코드와 약간 다르므로 숙지하자.
    private var _binding: FragmentIntro2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentIntro2Binding.inflate(inflater, container, false)
        return binding.root
    }

    //'뷰가 생성되었을 때 '라는 의미로, 여기서 우리가 원하는 코딩을 하면 된다.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {

            //intent에서 requireContext를 쓰는 것이 다르다.
            val intent = Intent(requireContext(), SelectActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}