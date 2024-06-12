package ru.it_cron.intern1.presentation.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.it_cron.intern1.R
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

    private val glide by inject<RequestManager>()

    private val reviewsAdapter by lazy {
        ReviewAdapter(
            context = requireContext(),
            glide = glide
        )
    }

    private val router: Router by inject()

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
        setupRecyclerView()
        setupButtonShowMore()
        setupIsHideFullPreviews()
        onClickBack()
    }

    private fun setupRecyclerView() {
        binding.rvReviews.adapter = reviewsAdapter
        viewModel.reviews.observe(viewLifecycleOwner) {
            reviewsAdapter.submitList(it)
        }
    }

    private fun setupButtonShowMore() {
        viewModel.showMore.observe(viewLifecycleOwner) { isVisibleButtonShowMore ->
            if (isVisibleButtonShowMore) {
                binding.showMore.visibility = View.VISIBLE
            } else {
                binding.showMore.visibility = View.GONE
            }
        }
    }

    private fun setupIsHideFullPreviews() {
        viewModel.isHideFullReviews.observe(viewLifecycleOwner) { isHideFullPreviews ->
            if (isHideFullPreviews) {
                binding.showMore.text = resources.getString(R.string.hide_text)
                binding.showMore.setOnClickListener {
                    viewModel.resetReviews()
                    binding.showMore.text = resources.getString(R.string.show_more)
                    binding.rvReviews.requestFocus()
                }
            } else {
                binding.showMore.text = resources.getString(R.string.show_more)
                binding.showMore.setOnClickListener {
                    viewModel.addReview()
                }
            }
        }
    }

    private fun onClickBack() {
        val toolBar = binding.tbReviews
        toolBar.setNavigationOnClickListener {
            router.exit()
        }
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