package ru.it_cron.android1.presentation.application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.android1.databinding.AppItemActiveBinding
import ru.it_cron.android1.databinding.AppItemInActiveBinding
import ru.it_cron.android1.databinding.TitleFilterItemBinding
import ru.it_cron.android1.domain.model.app.AppItem

class ApplicationAdapter : ListAdapter<AppItem, RecyclerView.ViewHolder>(AppItemDiffCallBack) {
    var serviceItemOnClickListener: ServiceItemOnClickListener? = null

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
                val view = AppItemActiveBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ServiceEnabledViewHolder(view)
            }

            ITEM_DISABLED_TYPE -> {
                val view = AppItemInActiveBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ServiceDisabledViewHolder(view)
            }

            else -> throw IllegalStateException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is AppItem.Header -> {
                val currentHolder = holder as HeaderViewHolder
                currentHolder.binding.tvTitleFilter.text = item.name
            }

            is AppItem.App -> {
                if (holder is ServiceEnabledViewHolder) {
                    holder.binding.tvAppName.text = item.name
                    holder.binding.root.setOnClickListener {
                        serviceItemOnClickListener?.onClickItem(item.name)
                    }
                }
                if (holder is ServiceDisabledViewHolder) {
                    holder.binding.tvAppName.text = item.name
                    holder.binding.root.setOnClickListener {
                        serviceItemOnClickListener?.onClickItem(item.name)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is AppItem.Header -> HEADER_TYPE
            is AppItem.App -> {
                if (item.isEnabled) ITEM_ENABLED_TYPE else ITEM_DISABLED_TYPE
            }
        }
    }

    object AppItemDiffCallBack : DiffUtil.ItemCallback<AppItem>() {
        override fun areItemsTheSame(oldItem: AppItem, newItem: AppItem): Boolean {
            return oldItem.getNames() == newItem.getNames()
        }

        override fun areContentsTheSame(oldItem: AppItem, newItem: AppItem): Boolean {
            return oldItem == newItem
        }

    }

    companion object {
        const val HEADER_TYPE = 400
        const val ITEM_ENABLED_TYPE = 500
        const val ITEM_DISABLED_TYPE = 600
    }

    class HeaderViewHolder(val binding: TitleFilterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ServiceEnabledViewHolder(val binding: AppItemActiveBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ServiceDisabledViewHolder(val binding: AppItemInActiveBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ServiceItemOnClickListener {
        fun onClickItem(name: String)
    }
}