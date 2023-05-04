package com.example.myapplication

import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_category.view.*
import kotlinx.android.synthetic.main.brand_name.view.*

class BrandAdapter(private val context: CategoryPage) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {
    var brandList = mutableListOf<BrandModel>()

    lateinit var listener : OnBrandClickListener

    private val checkStatus = SparseBooleanArray(0)
    private var onItemClickListener: ((BrandModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.brand_name,parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandAdapter.ViewHolder, position: Int) {

        val brandmodel: BrandModel = brandList[position]
        holder.name.text = brandmodel.name
        holder.cate.text = brandmodel.cate
        holder.userid.text = brandmodel.userid
        holder.grade.text = brandmodel.grade
    }

    override fun getItemCount(): Int {
        return brandList.size
    }

    fun setOnItemClickListener(listener: (BrandModel) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView =itemView.findViewById(R.id.name)
        val cate:TextView=itemView.findViewById(R.id.cate)
        val userid:TextView=itemView.findViewById(R.id.userid)
        val grade:TextView=itemView.findViewById(R.id.grade)

        init {
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val brand = brandList[position]
                        it(brand)

                        // 선택한 가게의 배경을 회색으로 바꿔줍니다.
                        if (!checkStatus.get(position, false)) {
                            itemView.setBackgroundColor(Color.LTGRAY)
                            checkStatus.put(position, true)
                        } else {
                            itemView.setBackgroundColor(Color.WHITE)
                            checkStatus.delete(position)
                        }
                    }
                }
            }
        }
    }
}