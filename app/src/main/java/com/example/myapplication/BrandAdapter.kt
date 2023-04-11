package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BrandAdapter(val context: MutableList<BrandModel>) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {
    var brandModelList = mutableListOf<BrandModel>()

    lateinit var listener : OnBrandClickListener



    //View Holder를 생성하고 View를 붙여주는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.brand_name,parent, false)
        return ViewHolder(view)
    }

    //생성된 View Holder에 데이터를 바인딩 해주는 메서드
    override fun onBindViewHolder(holder: BrandAdapter.ViewHolder, position: Int) {
        holder.bindItems(context[position])
    }

    //데이터의 개수를 반환하는 메서드
    override fun getItemCount(): Int {
        return brandModelList.size
    }

    // 화면에 표시 될 뷰를 저장하는 역할의 메서드
    // View를 재활용 하기 위해 각 요소를 저장해두고 사용
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bindItems(context: BrandModel) {
            val name = itemView.findViewById<TextView>(R.id.name)
            val cate = itemView.findViewById<TextView>(R.id.cate)
            val cate_num = itemView.findViewById<TextView>(R.id.cate_num)

            name.text = context.name
            cate.text = context.cate
            cate_num.text = context.cate_num

        }


    }
}