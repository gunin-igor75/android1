package ru.it_cron.intern1.presentation.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern1.databinding.FilterItemDisabledBinding
import ru.it_cron.intern1.databinding.FilterItemEnabledBinding
import ru.it_cron.intern1.databinding.TitleFilterItemBinding
import ru.it_cron.intern1.domain.model.filter.TypeItem

class FilterAdapter : ListAdapter<TypeItem, RecyclerView.ViewHolder>(TypeItemDiffCallBack) {

    var filterItemOnClickListener: FilterItemOnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_TYPE -> {
                val view = TitleFilterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(view)
            }

            ITEM_ENABLED_TYPE -> {
                val view = FilterItemEnabledBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FilterEnabledViewHolder(view)
            }

            ITEM_DISABLED_TYPE -> {
                val view = FilterItemDisabledBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FilterDisabledViewHolder(view)
            }

            else -> throw IllegalStateException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is TypeItem.Header -> {
                val currentHolder = holder as HeaderViewHolder
                currentHolder.binding.tvTitleFilter.text = item.name
            }

            is TypeItem.FilterItem -> {
                if (holder is FilterEnabledViewHolder) {
                    holder.binding.tvFilterNameEnabled.text = item.name
                    holder.binding.root.setOnClickListener {
                        filterItemOnClickListener?.onClickFilterItem(item.id)
                    }
                }
                if (holder is FilterDisabledViewHolder) {
                    holder.binding.tvFilterName.text = item.name
                    holder.binding.root.setOnClickListener {
                        filterItemOnClickListener?.onClickFilterItem(item.id)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is TypeItem.Header -> HEADER_TYPE
            is TypeItem.FilterItem -> {
                if (item.isEnabled) ITEM_ENABLED_TYPE else ITEM_DISABLED_TYPE
            }
        }
    }

    object TypeItemDiffCallBack : DiffUtil.ItemCallback<TypeItem>() {
        override fun areItemsTheSame(oldItem: TypeItem, newItem: TypeItem): Boolean {
            return oldItem.getIds() == newItem.getIds()

        }

        override fun areContentsTheSame(oldItem: TypeItem, newItem: TypeItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val HEADER_TYPE = 100
        const val ITEM_ENABLED_TYPE = 200
        const val ITEM_DISABLED_TYPE = 300
    }

    class HeaderViewHolder(val binding: TitleFilterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class FilterEnabledViewHolder(val binding: FilterItemEnabledBinding) :
        RecyclerView.ViewHolder(binding.root)

    class FilterDisabledViewHolder(val binding: FilterItemDisabledBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface FilterItemOnClickListener {
        fun onClickFilterItem(filterId: String)
    }
}