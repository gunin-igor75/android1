package ru.it_cron.intern1.presentation.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.it_cron.intern1.databinding.FragmentReviewsBinding
import ru.it_cron.intern1.domain.model.company.Review
import ru.it_cron.intern1.presentation.extension.getParcelableArrayListProvider

class ReviewsFragment : Fragment() {
    private var _binding: FragmentReviewsBinding? = null

    private val binding: FragmentReviewsBinding
        get() = _binding ?: throw IllegalStateException("FragmentReviewsBinding is null")


    private val reviews: List<Review> by lazy {
        arguments?.getParcelableArrayListProvider(REVIEWS) ?: throw IllegalStateException(
            "List<Review> is null"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val REVIEWS = "reviews"

        @JvmStatic
        fun newInstance(reviews: List<Review>) =
            ReviewsFragment().apply {
                arguments = bundleOf(REVIEWS to reviews)
            }
    }
}