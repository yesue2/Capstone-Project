package com.example.myapplication

import android.content.ContentValues.TAG
import android.graphics.Color
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.BrandNameBinding
import com.example.myapplication.matching.SelectedBrandModel

class BrandAdapter : RecyclerView.Adapter<BrandAdapter.Holder>() {

    private lateinit var listener: OnBrandClickListener
    var listData = mutableListOf<BrandModel>()
    private val selectedPositions = SparseBooleanArray()

    fun setOnBrandClickListener(listener: OnBrandClickListener) {
        this.listener = listener
    }

    fun setData(listData: MutableList<BrandModel>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    fun getSelectedBrands(): List<BrandModel> {
        val selectedBrands = mutableListOf<BrandModel>()
        for (i in 0 until selectedPositions.size()) {
            if (selectedPositions.valueAt(i)) {
                selectedBrands.add(listData[selectedPositions.keyAt(i)])
            }
        }
        return selectedBrands
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = BrandNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val brandmodel = listData[position]
        holder.bind(brandmodel)

        if (selectedPositions.get(position)) {
            holder.itemView.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Holder(val binding: BrandNameBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (selectedPositions.get(position)) {
                    selectedPositions.delete(position)
                } else {
                    selectedPositions.put(position, true)
                }
                notifyDataSetChanged()
                listener.onBrandClick(getSelectedBrands())
            }
        }

        fun bind(brandModel: BrandModel) {
            binding.name.text = brandModel.name
            binding.cate.text = brandModel.cate
        }
    }

    interface OnBrandClickListener {
        fun onBrandClick(selectedBrands: List<BrandModel>)
    }
}

