package com.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coco.R
import com.example.coco.databinding.FragmentPriceChangeBinding
import com.view.adapter.PriceListUpDownRVAdapter


class PriceChangeFragment : Fragment() {



    //코인가격 데이터의 변동을 살피기 위해 viewmodel을 사용한다.
    private val viewModel : MainViewModel by activityViewModels()

    private var _binding : FragmentPriceChangeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentPriceChangeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //이 함수가 어떤 함수인지는 mainviewmodel을 잘 가서 보자.
        viewModel.getAllSelectedCoinData()

        viewModel.arr15min.observe(viewLifecycleOwner, Observer {

            val priceListUpDownRVAdapter = PriceListUpDownRVAdapter(requireContext(),it)
            binding.price15m.adapter = priceListUpDownRVAdapter
            binding.price15m.layoutManager = LinearLayoutManager(requireContext())
        })
        viewModel.arr30min.observe(viewLifecycleOwner, Observer {
            val priceListUpDownRVAdapter = PriceListUpDownRVAdapter(requireContext(),it)
            binding.price30m.adapter = priceListUpDownRVAdapter
            binding.price30m.layoutManager = LinearLayoutManager(requireContext())
        })
        viewModel.arr45min.observe(viewLifecycleOwner, Observer {
            val priceListUpDownRVAdapter = PriceListUpDownRVAdapter(requireContext(),it)
            binding.price45m.adapter = priceListUpDownRVAdapter
            binding.price45m.layoutManager = LinearLayoutManager(requireContext())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

