package ru.it_cron.android1.presentation.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.android1.databinding.TitleFilterItemBinding
import ru.it_cron.android1.domain.model.HeaderFilter
import ru.it_cron.android1.presentation.filter.HeaderFilterAdapter.HeaderFilterViewHolder

class HeaderFilterAdapter : ListAdapter<HeaderFilter, HeaderFilterViewHolder>(HeaderFilterDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderFilterViewHolder {
        val view = TitleFilterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HeaderFilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderFilterViewHolder, position: Int) {
        val headerFilter = getItem(position)
        holder.binding.tvTitleFilter.text = headerFilter.name
    }

    object HeaderFilterDiffCallback : DiffUtil.ItemCallback<HeaderFilter>() {
        override fun areItemsTheSame(oldItem: HeaderFilter, newItem: HeaderFilter): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: HeaderFilter, newItem: HeaderFilter): Boolean {
            return oldItem == newItem
        }
    }

    class HeaderFilterViewHolder(val binding: TitleFilterItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}