package ru.it_cron.android1.presentation.contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.ContactItemBinding
import ru.it_cron.android1.domain.model.contacts.DayItem

class DaysItemAdapter(
    private val context: Context,
) :
    ListAdapter<DayItem, DaysItemAdapter.DaysItemViewHolder>(DaysItemDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysItemViewHolder {
        val view = ContactItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DaysItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaysItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    object DaysItemDiffCallback : DiffUtil.ItemCallback<DayItem>() {
        override fun areItemsTheSame(oldItem: DayItem, newItem: DayItem): Boolean {
            return oldItem.resId == newItem.resId
        }

        override fun areContentsTheSame(oldItem: DayItem, newItem: DayItem): Boolean {
            return oldItem == newItem
        }

    }

    inner class DaysItemViewHolder(private val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DayItem) {
            val currentAlpha = if (item.isCurrent) ALPHA_FULL else ALPHA_HALF
            binding.btSchedule.alpha = currentAlpha
            binding.btSchedule.setText(item.resId)
        }
    }

    private companion object {
        private const val ALPHA_FULL = 1f
        private const val ALPHA_HALF = 0.5f
    }
}