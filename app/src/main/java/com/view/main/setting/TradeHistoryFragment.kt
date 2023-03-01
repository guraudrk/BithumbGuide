package com.view.main.setting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
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
import com.view.main.MainViewModel


class TradeHistoryFragment : Fragment() {


    var inornot : Boolean =false
    private var _binding: FragmentTradeHistoryBinding? = null
    private val binding get() = _binding!!
    //최근 체결 내역을 가져오게 하는 fragment이다.

    //viewModel을 추가한다. 모델은 SelectViewModel이다.
    val viewModel: SelectViewModel by viewModels()
    private val viewModel1 : MainViewModel by activityViewModels()

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
        //코인의 리스트를 가져온다.
        viewModel.getCurrentCoinList()


        //현재가 조회를 누르면 생기는 일
        binding.btnSearch.setOnClickListener {

            val coin1 : String = binding.coinNmHistory.text.toString()
            //이 코드 안이 문제이다!

            //버튼을 누르면 기존 rv를 초기화해야 새로운 데이터가 잘 나온다.
//            binding.tradeHistoryRV.adapter!!.notifyDataSetChanged()


            if(coin1.equals("")||coin1==null){
                Toast.makeText(context,"공백입니다. 코인이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }


            //코인 이름에 관한 체결 내역 관련 정보를 받아오고,데이터를 파싱한다.


            val cointoupper = coin1.toUpperCase()

            //이렇게 미리 정의를 해 줘야 currentPriceResultList가 정의가 미리 되서 오류가 안난다.
            viewModel.getInterestCoinPriceData(cointoupper)
            for (coin in viewModel.currentPriceResultList) {
                //입력한 코인의 이름을 인스턴스로 받아온다.(문자는 반드시 대문자로!!)




                if (coin.coinName.equals(cointoupper)) {
                    viewModel.getInterestCoinPriceData(cointoupper)

                    //observe에 따라 this인지 viewLifecycleOwner인지 다를 수 있기 때문에 조심하자.
                    viewModel.tradeHistoryResult.observe(viewLifecycleOwner, Observer {
                        //위에서 리스트를 받아왔기 때문에 adapter의 안에 it을 쓸 수 있다.
                        TradeAdapter = TradeHistoryRVAdpater(requireContext(), it)

                        //리사이클러뷰의 어뎁터를 연결한다.
                        binding.tradeHistoryRV.adapter = TradeAdapter
                        //binding을 쓴다.

                        //실제로 화면을 보이게 하는 코드이다.
                        binding.tradeHistoryRV.layoutManager = LinearLayoutManager(requireContext())
                        inornot=true

                    }



                    )
                }

            }
            if(inornot==false)
            {
                Toast.makeText(context,"빗썸에 없는 코인이거나 철자가 틀렸습니다.", Toast.LENGTH_SHORT).show()

            }
        }


    }
}