package com.example.myapplication

import android.content.ContentValues.TAG
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.BrandNameBinding
import com.example.myapplication.matching.SelectedBrandModel

class BrandAdapter : RecyclerView.Adapter<BrandAdapter.Holder>() {

    lateinit var listener: OnBrandClickListener

    var listData = mutableListOf<BrandModel>()
    var selectedBrandList = mutableListOf<SelectedBrandModel>()


    //Holder를 생성하고 View를 붙여주는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = BrandNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        );

        return Holder(binding)
    }

    //생성된 Holder에 데이터를 바인딩 해주는 메서드
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val brandmodel = listData.get(position)
        holder.setBrandModel(brandmodel)
    }

    //데이터의 개수를 반환하는 메서드
    override fun getItemCount(): Int {
        return listData.size
    }


    // 화면에 표시 될 뷰를 저장하는 역할의 메서드
    // View를 재활용 하기 위해 각 요소를 저장해두고 사용
    class Holder(val binding: BrandNameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setBrandModel(memo: BrandModel) {
            binding.name.text = memo.name
            binding.cate.text = memo.cate
        }

        init {
            binding.root.setOnClickListener {
                // 클릭 이벤트 처리 코드 작성
                val name = binding.name.text
                Log.d(TAG, "Clicked on $name")
            }
        }

    }
}