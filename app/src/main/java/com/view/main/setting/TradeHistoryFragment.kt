package com.view.main.setting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coco.R
import com.example.coco.databinding.FragmentCoinInfoSeeBinding
import com.example.coco.databinding.FragmentTradeHistoryBinding
import com.example.coco.network.repository.DBRepository
import com.example.coco.network.repository.NetWorkRepository
import com.view.SelectViewModel
import com.view.adapter.SelectRVAdapter
import com.view.adapter.TradeHistoryRVAdpater


class TradeHistoryFragment : Fragment() {


    private var _binding : FragmentTradeHistoryBinding? = null
    private val binding get() = _binding!!
    //최근 체결 내역을 가져오게 하는 fragment이다.

    //viewModel을 추가한다. 모델은 SelectViewModel이다.
    val viewModel : SelectViewModel by viewModels()

    //어뎁터를 선언한다.
    private lateinit var TradeAdapter: TradeHistoryRVAdpater

    private val netWorkRepository = NetWorkRepository()
    private val dbRepository = DBRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTradeHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }


    //체결 내역을 실제로 가져오는 거시기.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //입력한 코인의 이름을 인스턴스로 받아온다.(문자는 반드시 대문자로!!)
       val coin = binding.coinNmHistory.text.toString().toUpperCase()

        //코인 이름에 관한 체결 내역 관련 정보를 받아오고,데이터를 파싱한다.
        viewModel.getInterestCoinPriceData(coin)

        //observe에 따라 this인지 viewLifecycleOwner인지 다를 수 있기 때문에 조심하자.
        viewModel.tradeHistoryResult.observe(viewLifecycleOwner, Observer {
            //위에서 리스트를 받아왔기 때문에 adapter의 안에 it을 쓸 수 있다.
            TradeAdapter = TradeHistoryRVAdpater(requireContext(),it)

            //리사이클러뷰의 어뎁터를 연결한다.
            binding.tradeHistoryRV.adapter = TradeAdapter
            //binding을 쓴다.

            //실제로 화면을 보이게 하는 코드이다.
            binding.tradeHistoryRV.layoutManager = LinearLayoutManager(requireContext())
        }

        )
    }


}