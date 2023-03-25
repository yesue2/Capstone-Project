package com.example.myapplication

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_category.view.*
import kotlinx.android.synthetic.main.brand_name.view.*
import androidx.annotation.NonNull;




class BrandAdapter(private val context: CategoryPage) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {
    var brandList = mutableListOf<Brand>()

    lateinit var listener : OnBrandClickListener

    private val checkStatus = SparseBooleanArray(0)


    fun setListData(data:MutableList<Brand>){
        brandList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.brand_name,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandAdapter.ViewHolder, position: Int) {

        val item: Brand=brandList[position]
        holder.name.text=item.name
        holder.cate.text=item.cate
        holder.num.text= item.num
        holder.cate_num.text= item.cate_num
       // holder.userid.text=item.userid
        //holder.grade.text= item.grade
    }

    override fun getItemCount(): Int {
        return brandList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val btn=itemView.btn_search
        val name:TextView=itemView.findViewById(R.id.name)
        val cate:TextView=itemView.findViewById(R.id.cate)
        val num:TextView=itemView.findViewById(R.id.num)
        val cate_num:TextView=itemView.findViewById(R.id.cate_num)
        val userid:TextView=itemView.findViewById(R.id.userid)
        val grade:TextView=itemView.findViewById(R.id.grade)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(this,itemView,adapterPosition,
                    checkStatus,
                    itemView.name.text,
                    itemView.cate.text,
                    itemView.num.text,
                    itemView.cate_num.text,
                    itemView.userid.text,
                    itemView.grade.text)
            }

        }


    }


}







