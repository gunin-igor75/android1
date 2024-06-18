package ru.it_cron.intern1.presentation.company

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.RequestManager
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.intern1.R
import ru.it_cron.intern1.constant.PN_FACEBOOK
import ru.it_cron.intern1.constant.PN_INSTAGRAM
import ru.it_cron.intern1.constant.PN_TELEGRAM
import ru.it_cron.intern1.constant.URL_EMAIL
import ru.it_cron.intern1.constant.URL_FACEBOOK
import ru.it_cron.intern1.constant.URL_HR
import ru.it_cron.intern1.constant.URL_INSTAGRAM
import ru.it_cron.intern1.constant.URL_TELEGRAM
import ru.it_cron.intern1.databinding.BlockCommunicationsCompanyBinding
import ru.it_cron.intern1.databinding.FragmentCompanyBinding
import ru.it_cron.intern1.domain.model.StateError
import ru.it_cron.intern1.domain.model.company.Review
import ru.it_cron.intern1.navigation.Screens
import ru.it_cron.intern1.presentation.extension.dpToPx
import ru.it_cron.intern1.presentation.extension.sendEmail
import ru.it_cron.intern1.presentation.extension.sendRequest
import ru.it_cron.intern1.presentation.utils.makeLinks

class CompanyFragment : Fragment() {
    private var _binding: FragmentCompanyBinding? = null
    private val binding: FragmentCompanyBinding
        get() = _binding ?: throw IllegalStateException("FragmentCompanyBinding is null")

    private val router: Router by inject()

    private val viewModel by viewModel<CompanyViewModel>()

    private val glide by inject<RequestManager>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        onClickMenuListener()
        onClickBack()
        createSpannableWord()
        onClickCommunicationsListener()
        observeViewModelError()
        observeViewModelReviews()
        sendApplication()

    }

    private fun sendApplication() {
        binding.btSendApp.btSendApp.setOnClickListener {
            router.navigateTo(Screens.openApplicationFragment())
        }
    }

    private fun observeViewModelReviews() {
        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            if (reviews.isEmpty()) {
                binding.inReviews.clReviews.visibility = View.GONE
            } else {
                onClickReviewsListener(reviews)
                binding.inReviews.clReviews.visibility = View.VISIBLE
                binding.inReviews.vpReviews.adapter = ReviewsViewPagerAdapter(
                    reviews = reviews,
                    glide = glide
                )
                setupReviewsIndicators(reviews.size)
                settingViewPager()
            }
        }

    }


    private fun settingViewPager() {
        binding.inReviews.vpReviews.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                changeBackgroundIndicator(position)
            }
        })
    }

    private fun observeViewModelError() {
        lifecycleScope.launch {
            viewModel.error.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { state ->
                when (state) {
                    is StateError.Error -> {
                        binding.inReviews.clReviews.visibility = View.GONE
                    }

                    StateError.ErrorInternet -> {
                        binding.inReviews.clReviews.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun onClickCommunicationsListener() {

        val blockCommunicationsBinding = BlockCommunicationsCompanyBinding.bind(binding.root)
        with(blockCommunicationsBinding) {
            tvInstagram.setOnClickListener {
                sendRequest(URL_INSTAGRAM, PN_INSTAGRAM)
            }
            tvFacebook.setOnClickListener {
                sendRequest(URL_FACEBOOK, PN_FACEBOOK)
            }
            tvTelegram.setOnClickListener {
                sendRequest(URL_TELEGRAM, PN_TELEGRAM)
            }
        }
        binding.inCommunicationsAddress.ivTelegram.setOnClickListener {
            sendRequest(URL_TELEGRAM, PN_TELEGRAM)
        }
        binding.inBlockClientCompany.tvContactsInfoEmail.setOnClickListener {
            sendEmail(URL_EMAIL)
        }
    }

    private fun createSpannableWord() {
        val view = binding.inJoinTeam.tvJoinTeamSlogan
        val listener = View.OnClickListener {
            sendEmail(URL_HR)
        }
        val color = ContextCompat.getColor(
            binding.root.context,
            R.color.orange
        )
        val spannableString = makeLinks(
            text = view.text.toString(),
            phrase = PHRASE,
            phraseColor = color,
            listener = listener
        )

        view.movementMethod = LinkMovementMethod.getInstance()
        view.setText(
            spannableString,
            TextView.BufferType.SPANNABLE
        )
    }

    private fun setupMenu() {
        binding.tbCompany.inflateMenu(R.menu.fragment_company)
    }


    private fun onClickMenuListener() {
        binding.tbCompany.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.mItCron -> {
                    true
                }

                R.id.mInstagram -> {
                    sendRequest(URL_INSTAGRAM, PN_INSTAGRAM)
                    true
                }

                R.id.mFacebook -> {
                    sendRequest(URL_FACEBOOK, PN_FACEBOOK)
                    true
                }

                R.id.mTelegram -> {
                    sendRequest(URL_TELEGRAM, PN_TELEGRAM)
                    true
                }

                else -> false
            }
        }
    }

    private fun onClickBack() {
        val toolBar = binding.tbCompany
        toolBar.setNavigationOnClickListener {
            router.exit()
        }
    }

    private fun setupReviewsIndicators(size: Int) {
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val margin = dpToPx(MARGIN)
        layoutParams.setMargins(margin, 0, 0, 0)
        for (i in 0 until size) {
            val indicator = ImageView(requireContext())
            indicator.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.reviews_indicator_inactive
                )
            )
            indicator.layoutParams = layoutParams
            binding.inReviews.llIndicators.addView(indicator)
        }
    }

    private fun changeBackgroundIndicator(position: Int) {
        with(binding.inReviews) {
            val count = llIndicators.childCount
            for (i in 0 until count) {
                val indicator = llIndicators.getChildAt(i) as ImageView
                if (i == position) {
                    indicator.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.reviews_indicator_active
                        )
                    )
                } else {
                    indicator.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.reviews_indicator_inactive
                        )
                    )
                }
            }
        }
    }

    private fun onClickReviewsListener(reviews: List<Review>) {
        binding.inReviews.llSeeAll.setOnClickListener {
            router.navigateTo(Screens.openReviewsFragment(reviews))
        }
    }

    companion object {

        private const val PHRASE = "hr@it-cron.ru"
        private const val MARGIN = 8

        @JvmStatic
        fun newInstance() = CompanyFragment()
    }
}
