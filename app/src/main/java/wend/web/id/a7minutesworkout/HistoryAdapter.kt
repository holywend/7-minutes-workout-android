package wend.web.id.a7minutesworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import wend.web.id.a7minutesworkout.databinding.LinearlayoutHistoryBinding

class HistoryAdapter(private val items: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: LinearlayoutHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        val llHistoryItem = binding.llHistoryItem
        val tvIndex = binding.tvIndex
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LinearlayoutHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items[position]
        val index = position + 1
        holder.tvIndex.text = index.toString()
        holder.tvItem.text = date
        if (position % 2 == 0 ){
            holder.llHistoryItem.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.gray_200)
            )
        }else {
            holder.llHistoryItem.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.white)
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}