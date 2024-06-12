package ru.it_cron.intern1.presentation.reviews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
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

    private val reviewsAdapter by lazy {
        ReviewAdapter()
    }

    private val viewModel: ReviewsViewModel by viewModel{ parametersOf(reviews) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ReviewsFragment", viewModel.initialReviews.toString())
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvReviews.adapter = reviewsAdapter

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