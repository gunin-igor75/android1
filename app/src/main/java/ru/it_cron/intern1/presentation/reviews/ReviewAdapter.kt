package ru.it_cron.intern1.presentation.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern1.databinding.CardReviewBinding
import ru.it_cron.intern1.domain.model.company.Review

class ReviewAdapter: ListAdapter<Review, ReviewAdapter.ReviewViewHolder>(ReviewItemDiffCallBack) {

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


    class ReviewViewHolder(val binding: CardReviewBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(review: Review) {
            with(binding){
                tvAvatar.text = review.customerName
                tvAvatarJob.text = review.company
                tvContent.text = review.text
            }
        }
    }
}