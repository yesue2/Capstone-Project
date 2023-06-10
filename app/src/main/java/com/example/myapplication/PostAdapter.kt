import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Board
import com.example.myapplication.R

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private val postList = ArrayList<Board>()
    private var listener: OnItemClickListener? = null
    private var selectedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = postList[position]

        holder.titleTextView.text = currentPost.title
        holder.contentTextView.text = currentPost.content
        holder.dateTextView.text = currentPost.date

        // 클릭한 아이템에 대해 선택된 상태를 표시
        holder.itemView.isSelected = position == selectedItemPosition

        // 애니메이션 적용
        setAnimation(holder.itemView, position)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
            setSelectedItem(position)
        }

        if (position == postList.size - 1) {
            holder.dividerView.visibility = View.GONE
        } else {
            holder.dividerView.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setData(data: List<Board>) {
        postList.clear()
        postList.addAll(data)
    }

    fun getItem(position: Int): Board {
        return postList[position]
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setSelectedItem(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
    }

    private fun setAnimation(view: View, position: Int) {
        if (position > selectedItemPosition) {
            val animation = AnimationUtils.loadAnimation(view.context, R.anim.slide_in_bottom)
            view.startAnimation(animation)
            selectedItemPosition = position
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.postTitleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.postContentTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.postDateTextView)
        val dividerView: View = itemView.findViewById(R.id.dividerView)
    }
}
