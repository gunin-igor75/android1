package ru.it_cron.android1.presentation.cases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.it_cron.android1.databinding.CaseItemBinding
import ru.it_cron.android1.domain.model.Case
import ru.it_cron.android1.presentation.cases.CasesAdapter.CaseViewHolder

class CasesAdapter(
    private val context: Context
): ListAdapter<Case, CaseViewHolder>(CaseItemDiffCallback) {

    var caseOnClickListener: CaseOnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val  view = CaseItemBinding.inflate(
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
            Glide.with(context)
                .load(case.image)
                .override(300, 200)
                .into(ivCase)
            root.setOnClickListener {
                caseOnClickListener?.onClickCase(case.id)
            }
        }
    }


    class CaseViewHolder(val binding: CaseItemBinding): RecyclerView.ViewHolder(binding.root)

    object CaseItemDiffCallback: DiffUtil.ItemCallback<Case>(){
        override fun areItemsTheSame(oldItem: Case, newItem: Case): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Case, newItem: Case): Boolean {
            return oldItem == newItem
        }
    }

    interface CaseOnClickListener{
        fun onClickCase(caseId: String)
    }
}