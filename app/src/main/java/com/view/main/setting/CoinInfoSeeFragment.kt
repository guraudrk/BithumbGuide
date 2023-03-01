package com.view.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.coco.databinding.FragmentCoinInfoSeeBinding
import com.example.coco.network.repository.NetWorkRepository
import com.view.SelectViewModel
import com.view.adapter.CoinInfoSeeRVAdapter
import com.view.adapter.SelectRVAdapter
import com.view.main.MainViewModel
import timber.log.Timber


class CoinInfoSeeFragment : Fragment() {




    //일치하는 수가 있는지 없는지를 설정하는 변수
   private var inornot :Boolean = false

    //viewModel을 추가한다. 모델은 SelectViewModel이다.
     val viewModel : SelectViewModel by viewModels()
    private val netWorkRepository = NetWorkRepository()

    //호가
    //이거를 쓰기 위해서는 volley dependence를 추가해야 한다.
    private var requestQueue: RequestQueue? = null


    private lateinit var CoinInfoSeeRVAdapter: CoinInfoSeeRVAdapter

    private var _binding : FragmentCoinInfoSeeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCoinInfoSeeBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }



    /*
    api는 그대로 쓰고, 리스트에 입력한게 있으면 값을 가져오고 아니면 다른 값을 호출하는 식으로 한다.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //코인의 리스트를 가져온다.

        viewModel.getCurrentCoinList()
        binding.btnSearch.setOnClickListener {
            //리스트 갱신
            binding.openingPrice.text = ""
            binding.closingPrice.text = ""
            binding.minPrice.text = ""
            binding.maxPrice.text = ""
            binding.unitsTraded.text = ""
            binding.accTradeValue.text = ""
            binding.prevClosingPrice.text = ""
            binding.unitsTraded24H.text = ""
            binding.accTradeValue24H.text = ""
            binding.fluctate24H.text = ""
            binding.fluctateRate24H.text = ""

            //코인 이름을 적으면, 그것을 변수에 담기
            val getCoinName :String = binding.coinNm.text.toString()

            //입력값 체크하기
            if(getCoinName.equals("")||getCoinName==null){
                Toast.makeText(context,"공백입니다. 코인이름을 입력해주세요.",Toast.LENGTH_SHORT).show()
            }

                //일치하는 이름이 있다면 이름에 관한 정보를 보여준다. binding을 통해 text정보를 보여준다.
             for(coin in viewModel.currentPriceResultList)
                {
                 //이미 입력해둔 코인 이름에 대한 인스턴스를 만든다.
                    val cointoupper = getCoinName.toUpperCase()
                    //만약 빗썸 내에 있는 코인을 알맞게 입력했다면
                 if(coin.coinName.equals(cointoupper)) {//원원원원개개원개원원%
                     binding.openingPrice.text = coin.coinInfo.opening_price + "원"
                     binding.closingPrice.text = coin.coinInfo.closing_price + "원"
                     binding.minPrice.text = coin.coinInfo.min_price + "원"
                     binding.maxPrice.text = coin.coinInfo.max_price + "원"
                     binding.unitsTraded.text = coin.coinInfo.units_traded + "개"
                     binding.accTradeValue.text = coin.coinInfo.acc_trade_value + "원"
                     binding.prevClosingPrice.text = coin.coinInfo.prev_closing_price + "원"
                     binding.unitsTraded24H.text = coin.coinInfo.units_traded_24H + "개"
                     binding.accTradeValue24H.text = coin.coinInfo.acc_trade_value_24H + "원"
                     binding.fluctate24H.text = coin.coinInfo.fluctate_24H + "원"
                     binding.fluctateRate24H.text = coin.coinInfo.fluctate_rate_24H + "%"
                     //리스트에 있는지없는지를 확인해주는 변수를 설정한다.
                     Toast.makeText(context,"정보 불러오기에 성공했습니다.",Toast.LENGTH_SHORT).show()
                     inornot = true
                 }
                }

            //리스트에 있는지 없는지에 따라 해당 text를 적을지 안적을지 정한다.
            if(inornot==false)
            {
                Toast.makeText(context,"빗썸에 없는 코인이거나 철자가 틀렸습니다.",Toast.LENGTH_SHORT).show()
            }




            }


        }
    }





