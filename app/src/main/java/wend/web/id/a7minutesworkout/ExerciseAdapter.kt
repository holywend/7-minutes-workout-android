package wend.web.id.a7minutesworkout

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import wend.web.id.a7minutesworkout.databinding.ItemTextviewActivityBinding
import kotlin.coroutines.coroutineContext

class ExerciseAdapter (private val items: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

        class ViewHolder(binding: ItemTextviewActivityBinding): RecyclerView.ViewHolder(binding.root) {
            val tvItem = binding.tvItem
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTextviewActivityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ExerciseModel = items[position]
        holder.tvItem.text = item.id.toString()
        when {
            item.isCurrent -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.tvItem.context, R.drawable.item_shape_activity_bg_emerald)
                holder.tvItem.setTextColor(
                    ContextCompat.getColor(holder.tvItem.context,R.color.white)
                )
            }
            item.isCompleted -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.tvItem.context,R.drawable.item_shape_activity_bg_blue)
                holder.tvItem.setTextColor(
                    ContextCompat.getColor(holder.tvItem.context,R.color.white)
                )
            }
            else -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.tvItem.context,R.drawable.item_shape_activity_bg_gray)
                holder.tvItem.setTextColor(
                    ContextCompat.getColor(holder.tvItem.context,R.color.gray_800)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}