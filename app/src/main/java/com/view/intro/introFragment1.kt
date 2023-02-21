package com.view.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.coco.R
import com.example.coco.databinding.FragmentIntro1Binding


class introFragment1 : Fragment() {

    //fragment에서 바인딩을 할 수 있게 하는 코드이다.
    //우리가 알던 바인딩 코드와 약간 다르므로 숙지하자.
    private var _binding : FragmentIntro1Binding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntro1Binding.inflate(inflater,container,false)
        return binding.root
    }


    //이제 본격적으로 어떤 동작을 하면 어떻게 작용을 하는지에 대해 코드를 작성해보자.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_introFragment1_to_introFragment2)
        }
    }

    //fragment가 없어졌을 때 처리하는 부분이다.
    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}