package ru.it_cron.android1.presentation.filter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.FilterItemBinding
import ru.it_cron.android1.domain.model.FilterItem
import ru.it_cron.android1.presentation.filter.FilterAdapter.FilterViewHolder

class FilterAdapter : ListAdapter<FilterItem, FilterViewHolder>(FilterDiffCallBack) {

    val filterOnClickListener: FilterOnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = FilterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filterItem = getItem(position)
        with(holder.binding) {
            tvFilterName.text = filterItem.name
            tvFilterName.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        root.context,
                        if (filterItem.inChecked) R.color.white
                        else R.color.black
                    )
                )
            )
            cvFilter.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    root.context,
                    if (filterItem.inChecked) R.color.orange
                    else R.color.white
                )
            )
            ivFilter.setImageResource(
                if (filterItem.inChecked) R.drawable.ic_selected
                else R.drawable.ic_disabled
            )
        }
        holder.binding.root.setOnClickListener {
            filterOnClickListener?.onClickFilter(filterItem)
        }
    }

    class FilterViewHolder(val binding: FilterItemBinding) : RecyclerView.ViewHolder(binding.root)

    object FilterDiffCallBack : DiffUtil.ItemCallback<FilterItem>() {
        override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
            return oldItem == newItem
        }
    }

    interface FilterOnClickListener {
        fun onClickFilter(filterItem: FilterItem)
    }
}