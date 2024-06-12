package ru.it_cron.intern1.presentation.reviews

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.it_cron.intern1.R
import ru.it_cron.intern1.databinding.CardReviewBinding
import ru.it_cron.intern1.domain.model.company.Review

class ReviewAdapter(
    private val context: Context,
    private val glide: RequestManager,
) : ListAdapter<Review, ReviewAdapter.ReviewViewHolder>(ReviewItemDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = CardReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    object ReviewItemDiffCallBack: DiffUtil.ItemCallback<Review>(){
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
           return oldItem == newItem
        }
    }


    inner class ReviewViewHolder(val binding: CardReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            val hasVisibleButtonReadFull = binding.tvContent.ellipsize == TextUtils.TruncateAt.END

            with(binding){
                tvAvatar.text = review.customerName
                tvAvatarJob.text = review.company
                tvContent.text = review.text
                glide.load(review.urlIcon)
                    .circleCrop()
                    .into(ivIconOwnerReview)

                binding.tvReadFull.visibility =
                    if (hasVisibleButtonReadFull) View.VISIBLE else View.GONE

                tvReadFull.setOnClickListener {
                    if (tvContent.maxLines == Int.MAX_VALUE) {
                        tvContent.maxLines = context.resources.getInteger(R.integer.maxLinesReview)
                        tvReadFull.text = context.resources.getString(R.string.read_full)
                    } else {
                        tvContent.maxLines = Int.MAX_VALUE
                        tvReadFull.text = context.resources.getString(R.string.hide_text)
                    }
                }
            }
        }
    }
}