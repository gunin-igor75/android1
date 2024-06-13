package ru.it_cron.intern1.presentation.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.it_cron.intern1.databinding.ReviewsItemBinding
import ru.it_cron.intern1.domain.model.company.Review

class ReviewsViewPagerAdapter(
    private val reviews: List<Review>,
    private val glide: RequestManager,
) : RecyclerView.Adapter<ReviewsViewPagerAdapter.PagerViewHolderReviews>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolderReviews {
        val view = ReviewsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerViewHolderReviews(view)
    }

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: PagerViewHolderReviews, position: Int) {
        val review = reviews[position]
        with(holder.binding) {
            tvReviewsContent.text = review.text
            tvReviewsJob.text = review.company
            tvReviewsName.text = review.customerName
            glide.load(review.urlIcon)
                .circleCrop()
                .into(ivReview)
        }
    }

    class PagerViewHolderReviews(val binding: ReviewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}