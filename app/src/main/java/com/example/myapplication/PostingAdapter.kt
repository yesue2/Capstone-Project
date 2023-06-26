package com.example.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class PostingAdapter(
    private val storeList: List<Store>,
    private val onStoreClickListener: OnStoreClickListener,
    private val selectedStoreName: String?
    ) : RecyclerView.Adapter<PostingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = storeList[position]
        holder.bind(store)
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(store: Store) {
            itemView.findViewById<TextView>(R.id.storeName).text = store.storeName
            itemView.findViewById<TextView>(R.id.minOrder).text = store.minOrder
            itemView.findViewById<TextView>(R.id.timeTaken).text = store.timeTaken

            // 가게 아이템 클릭 시 onStoreClickListener를 통해 클릭 이벤트 전달
            itemView.setOnClickListener {
                onStoreClickListener.onStoreClick(store)
            }

            // 선택한 가게인 경우 텍스트 색상 변경
            if (store.storeName == selectedStoreName) {
                itemView.setBackgroundColor(Color.YELLOW)
            } else {
                itemView.setBackgroundColor(Color.WHITE)
            }
        }
    }

    interface OnStoreClickListener {
        fun onStoreClick(store: Store)
    }
}
