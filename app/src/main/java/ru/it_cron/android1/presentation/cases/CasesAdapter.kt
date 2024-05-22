package ru.it_cron.android1.presentation.cases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.it_cron.android1.databinding.CaseItemBinding
import ru.it_cron.android1.domain.model.cases.Case
import ru.it_cron.android1.domain.model.cases.CaseBox
import ru.it_cron.android1.presentation.cases.CasesAdapter.CaseViewHolder

class CasesAdapter(
    private val glide: RequestManager,
) : ListAdapter<Case, CaseViewHolder>(CaseItemDiffCallback) {

    var caseOnClickListener: CaseOnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val view = CaseItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        val case = getItem(position)
        with(holder.binding) {
            tvCaseTitle.text = case.title
            ivCase.visibility = View.VISIBLE
            glide.load(case.image)
                .override(WIDTH, HEIGHT)
                .into(ivCase)
            root.setOnClickListener {
                val caseNext = if (position == itemCount - 1) null else getItem(position + 1)
                val caseBox = CaseBox(case, caseNext)
                caseOnClickListener?.onClickCase(caseBox)
            }
        }
    }

    class CaseViewHolder(val binding: CaseItemBinding) : RecyclerView.ViewHolder(binding.root)

    object CaseItemDiffCallback : DiffUtil.ItemCallback<Case>() {
        override fun areItemsTheSame(oldItem: Case, newItem: Case): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Case, newItem: Case): Boolean {
            return oldItem == newItem
        }
    }

    interface CaseOnClickListener {
        fun onClickCase(caseBox: CaseBox)
    }

    companion object {
        private const val WIDTH = 300
        private const val HEIGHT = 300
    }
}