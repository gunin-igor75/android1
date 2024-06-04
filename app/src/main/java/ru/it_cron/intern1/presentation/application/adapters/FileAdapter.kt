package ru.it_cron.intern1.presentation.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern1.databinding.CardFileItemBinding
import ru.it_cron.intern1.databinding.CardImageItemBinding
import ru.it_cron.intern1.domain.model.app.FileItem

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
                holderPhoto.bind(item)
            }

            false -> {
                val holderFile = holder as FileViewHolder
                holderFile.bind(item)
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

    inner class FileViewHolder(val binding: CardFileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fileItem: FileItem) {
            with(binding) {
                tvFileType.text = fileItem.extension
                tvFileName.text = fileItem.getName()
                tvFileSize.text = fileItem.getStringSize()
                tvFileType.background = ContextCompat.getDrawable(context, fileItem.colorId)
            }
            binding.btRemoveFile.btRemove.setOnClickListener {
                fileItemOnClickListener?.onClickItem(fileItem)
            }
        }
    }

    inner class PhotoViewHolder(val binding: CardImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fileItem: FileItem) {
            with(binding) {
                ivFile.setImageURI(fileItem.uri)
            }
            binding.btRemoveFile.btRemove.setOnClickListener {
                fileItemOnClickListener?.onClickItem(fileItem)
            }
        }
    }

    interface FileItemOnClickListener {
        fun onClickItem(fileItem: FileItem)
    }
}