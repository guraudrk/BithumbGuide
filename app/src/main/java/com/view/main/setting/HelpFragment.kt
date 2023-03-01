package com.view.main.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.coco.R
import com.example.coco.databinding.FragmentCoinInfoSeeBinding
import com.example.coco.databinding.FragmentCoinListBinding
import com.example.coco.databinding.FragmentHelpBinding
import com.view.main.AssetStatusModel


class HelpFragment : Fragment() {


    var inornot : Boolean = false


    //뷰바인딩!
    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AssetStatusModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHelpBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //입력한 코인이 맞는 코인인지 아닌지에 대한 변수


        //코인 입력창, 결과값들에 대한 바인딩
        val helpButton = binding.btnSearchHelp




        //4.for문을 통해 코인이 있는지 없는지 확인하고, 있다면 입출금 형식을 정해준다.

        //4.1.우선 리스트를 가져온다.
        viewModel.getAssetStatusList()


        //버튼 클릭시 나오는 현상
        helpButton.setOnClickListener {
            //리스트 초기화
            binding.depositStatus.text = ""
            binding.withdrawalStatus.text = ""
            binding.coinname.text = ""


            val coinHelp :String = binding.coinHelp.text.toString().uppercase()

            //적은 코인 이름을 변수에 담기(대문자로!)
            val coinName :String = coinHelp


            //공백인지 아닌지 체크
            if(coinName.equals("")||coinName==null){
                Toast.makeText(context,"공백입니다. 코인이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }


            //for문에서 task를 진행시킨다.
            for(coin in viewModel.AssetStatusResultList)
            {
                //만약 코인 이름을 알맞게 입력했다면
                if(coin.coinName.equals(coinName))
                {

                    //조건에 따라 텍스트를 설정한다.

                    //코인 이름 설정
                    binding.coinname.text = coin.coinName

                    //입금이 가능하다면,가능하지 않다면
                    if(coin.coinInfo.deposit_status==1){
                        binding.depositStatus.text = "입금 가능합니다. 안심하고 입금하세요!"

                    }
                    else{
                        binding.depositStatus.text = "입금 불가능합니다. 자세한 정보는 빗썸에서 확인하세요!"
                    }

                    //출금이 가능하다면, 가능하지 않다면
                    if(coin.coinInfo.withdrawal_status==1){
                        binding.withdrawalStatus.text = "출금 가능합니다. 안심하고 출금하세요!"

                    }
                    else{
                        binding.withdrawalStatus.text = "출금 불가능합니다. 자세한 정보는 빗썸에서 확인하세요!"
                    }
                    inornot = true
                    Toast.makeText(context,"정보 불러오기에 성공했습니다.",Toast.LENGTH_SHORT).show()

                }
            }

            if(inornot==false)
            {
                Toast.makeText(context,"빗썸에 없는 코인이거나 철자가 틀렸습니다.",Toast.LENGTH_SHORT).show()
            }
        }



    }
}