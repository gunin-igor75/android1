package ru.it_cron.android1.presentation.application.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.android1.databinding.CardFileItemBinding
import ru.it_cron.android1.databinding.CardImageItemBinding
import ru.it_cron.android1.domain.model.app.FileItem

class FileAdapter(
    private val context: Context,
) : ListAdapter<FileItem, RecyclerView.ViewHolder>(FileItemDiffCallBack) {
    var fileItemOnClickListener: FileItemOnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FILE_TYPE -> {
                val view = CardFileItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FileViewHolder(view)
            }

            PHOTO_TYPE -> {
                val view = CardImageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PhotoViewHolder(view)
            }

            else -> throw IllegalStateException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (item.isPhoto) {
            true -> {
                val holderPhoto = holder as PhotoViewHolder
                with(holderPhoto.binding) {
                    ivFile.setImageURI(item.uri)
                }
                holder.binding.btRemoveFile.btRemove.setOnClickListener {
                    fileItemOnClickListener?.onClickItem(item)
                }
            }

            false -> {
                val holderFile = holder as FileViewHolder
                with(holderFile.binding) {
                    tvFileType.text = item.extension
                    tvFileName.text = item.getName()
                    tvFileSize.text = item.getStringSize()
                    tvFileType.background = ContextCompat.getDrawable(context, item.colorId)
                }
                holder.binding.btRemoveFile.btRemove.setOnClickListener {
                    fileItemOnClickListener?.onClickItem(item)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isPhoto) PHOTO_TYPE else FILE_TYPE
    }

    object FileItemDiffCallBack : DiffUtil.ItemCallback<FileItem>() {
        override fun areItemsTheSame(oldItem: FileItem, newItem: FileItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FileItem, newItem: FileItem): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val FILE_TYPE = 800
        const val PHOTO_TYPE = 900
    }

    class FileViewHolder(val binding: CardFileItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class PhotoViewHolder(val binding: CardImageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface FileItemOnClickListener {
        fun onClickItem(fileItem: FileItem)
    }
}