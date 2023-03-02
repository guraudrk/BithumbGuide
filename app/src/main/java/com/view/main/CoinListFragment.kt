package com.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.db.entity.InterestCoinEntity
import com.example.coco.R
import com.example.coco.databinding.FragmentCoinListBinding
import com.view.adapter.CoinListRVAdapater


class CoinListFragment : Fragment() {



    //뷰모델을 사용하기 위한 코드.
    private val viewModel : MainViewModel by activityViewModels()

    //뷰바인딩!
    private var _binding : FragmentCoinListBinding? = null
    private val binding get() = _binding!!



    //사용자가 선택한 것과 선택하지 않은 것이다.
    private val selectedList = ArrayList<InterestCoinEntity>()
    private val unselectedList = ArrayList<InterestCoinEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentCoinListBinding.inflate(layoutInflater)
        val view = binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //모든 코인 데이터를 가져온다.
        viewModel.getAllInterestCoinData()

        //라이브 데이터가 변경된 것을 관찰한다.
        viewModel.selectedCoinList.observe(viewLifecycleOwner, Observer {



            //라이브 데이터여서 데이터가 변동될 때마다 리스트가 바뀌는데
            //그때마다 클리어를 해야 한다.
            //어차피 밑의 코드에서 다 해준다잉.
            selectedList.clear()
            unselectedList.clear()

            //아이템을 검증한다.
            for(item in it){
                if(item.selected){
                    selectedList.add(item)
                }
                else{
                    unselectedList.add(item)
                }
            }
            setSelectedListRV()
        })



    }

    //어뎁터를 세팅하는 함수이다.
    private fun setSelectedListRV(){

        //선택한 함수를 보여주는 리사이클러뷰
        val selectedRVAdapater = CoinListRVAdapater(requireContext(),selectedList)
        binding.selectedCoinRV.adapter = selectedRVAdapater
        binding.selectedCoinRV.layoutManager = LinearLayoutManager(requireContext())


        //아이템 클릭에 관한 함수!
        //이건 자료를 찾아봐야 한다.
        selectedRVAdapater.itemClick = object : CoinListRVAdapater.ItemClick{
            override fun onClick(view: View, position: Int) {

                //클릭 시 관련 정보를 업데이트 한다.
                viewModel.updateInterestCoinData(selectedList[position])
            }
        }



        //선택 안한거도 보여주는 리사이클러뷰
        val unselectedRVAdapater = CoinListRVAdapater(requireContext(),unselectedList)
        binding.unSelectedCoinRV.adapter = unselectedRVAdapater
        binding.unSelectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        unselectedRVAdapater.itemClick = object : CoinListRVAdapater.ItemClick{
            override fun onClick(view: View, position: Int) {

                //클릭 시 관련 정보를 업데이트한다.
                viewModel.updateInterestCoinData(unselectedList[position])

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    }

