package ru.it_cron.intern1.presentation.case_details

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.intern1.R
import ru.it_cron.intern1.databinding.AppAllowsItemBinding
import ru.it_cron.intern1.databinding.BlockButtonCaseBinding
import ru.it_cron.intern1.databinding.BlockNextCaseBinding
import ru.it_cron.intern1.databinding.BlockTaskCaseBinding
import ru.it_cron.intern1.databinding.BlockTechnologyPlatformCaseBinding
import ru.it_cron.intern1.databinding.FragmentCaseBinding
import ru.it_cron.intern1.domain.model.StateError
import ru.it_cron.intern1.domain.model.StateScreen
import ru.it_cron.intern1.domain.model.cases.CaseBox
import ru.it_cron.intern1.domain.model.cases.CaseDetails
import ru.it_cron.intern1.domain.model.filter.ContainerImage
import ru.it_cron.intern1.navigation.Screens
import ru.it_cron.intern1.presentation.extension.openInternet
import ru.it_cron.intern1.presentation.extension.roundCorners
import ru.it_cron.intern1.presentation.extension.sendEmail
import ru.it_cron.intern1.presentation.utils.setupText


class CaseFragment : Fragment() {

    private var _binding: FragmentCaseBinding? = null

    private val binding: FragmentCaseBinding
        get() = _binding ?: throw IllegalStateException("FragmentCaseBinding is null")


    private val caseBox: CaseBox by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(CASE_BOX, CaseBox::class.java) as CaseBox
        } else {
            arguments?.getParcelable(CASE_BOX) ?: throw IllegalArgumentException(
                "CaseBox s null"
            )
        }
    }
    private val viewModel: CaseDetailsViewModel by viewModel()

    private val router: Router by inject()

    private var imageState: MutableStateFlow<ContainerImage> = MutableStateFlow(ContainerImage())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBarApp()
        observeViewModel()
        observeViewModelError()
        onClickImages()
        onClockEmail()
        onClickHomeTitle()
        onClickSendApp()
    }

    private fun observeViewModel() {
        val caseId = caseBox.case.id
        viewModel.getCase(caseId)
        lifecycleScope.launch {
            viewModel.caseDetails.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { state ->
                when (state) {
                    is StateScreen.Initial -> {}

                    is StateScreen.Loading -> {
                        binding.pbCase.visibility = View.VISIBLE
                    }

                    is StateScreen.Success -> {
                        binding.pbCase.visibility = View.GONE
                        setupContent(state.value)
                        imageState.value = ContainerImage(
                            state.value.caseColorId, state.value.images
                        )
                    }
                }
            }
        }
    }

    private fun observeViewModelError() {
        lifecycleScope.launch {
            viewModel.error.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { state ->
                when (state) {
                    is StateError.Error -> {
                        Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                    }

                    StateError.ErrorInternet -> {
                        router.replaceScreen(Screens.openErrorFragment())
                    }
                }
            }
        }
    }

    private fun setupToolBarApp() {
        val toolBar = binding.tbCase
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolBar)
        toolBar.setNavigationOnClickListener {
            router.exit()
        }
    }

    private fun setupContent(caseDetails: CaseDetails) {
        setupBlockTitleTask(caseDetails)
        setupUiBlockImages(caseDetails)
        setupUiBlockAppAllows(caseDetails)
        setupUiBlockTechnologiesPlatforms(caseDetails)
        setupBlockNextCase()
        setupBlockButton(caseDetails)
    }

    private fun checkAndSetupGone(isEmpty: Boolean, vararg view: View): Boolean {
        if (isEmpty) {
            view.forEach { it.visibility = View.GONE }
            return true
        }
        return false
    }

    private fun setupBlockTitleTask(caseDetails: CaseDetails) {
        val currentBinding = BlockTaskCaseBinding.bind(binding.root)
        with(currentBinding) {
            if (!checkAndSetupGone(
                    caseBox.case.title.isEmpty(), tvBlockCaseTitle
                )
            ) {
                tvBlockCaseTitle.text = caseBox.case.title
            }
            if (!checkAndSetupGone(
                    caseBox.case.image.isEmpty(), ivBlockCase
                )
            ) {
                loadImage(ivBlockCase, caseBox.case.image)
            }
            if (!checkAndSetupGone(
                    caseDetails.task.isEmpty(), tvBlockTaskContent
                )
            ) {
                tvBlockTaskContent.text = caseDetails.task
            }
        }
    }

    private fun setupUiBlockImages(caseDetails: CaseDetails) {
        with(binding.icBlockImages) {
            if (!checkAndSetupGone(
                    caseDetails.images.isEmpty(), llBlockImages
                )
            ) {
                llBlockImages.setBackgroundColor(caseDetails.caseColorId)
                val url1 = caseDetails.images[0]
                val url2 = caseDetails.images[1]
                loadImage(ivCase1, url1, resources.getInteger(R.integer.corner_radius))
                loadImage(ivCase2, url2, resources.getInteger(R.integer.corner_radius))
            }
        }
    }

    private fun setupUiBlockAppAllows(caseDetails: CaseDetails) {
        with(binding) {
            if (!checkAndSetupGone(
                    caseDetails.featureTitle.isEmpty(), tvAppAllows
                )
            ) {
                tvAppAllows.text = caseDetails.featureTitle
            }
            if (!checkAndSetupGone(
                    caseDetails.featuresDone.isEmpty(), llBlockCaseAppAllows
                )
            ) {
                caseDetails.featuresDone.forEach {
                    val view = LayoutInflater.from(requireContext())
                        .inflate(R.layout.app_allows_item, null)
                    val currentBinding = AppAllowsItemBinding.bind(view)
                    currentBinding.tvAppAllowsText.text = it
                    llBlockCaseAppAllows.addView(view)
                }
            }
        }
    }

    private fun setupUiBlockTechnologiesPlatforms(caseDetails: CaseDetails) {
        val currentBinding = BlockTechnologyPlatformCaseBinding.bind(binding.root)
        with(currentBinding) {
            cvTechnologyPlatform.setCardBackgroundColor(caseDetails.caseColorId)
            if (!checkAndSetupGone(
                    caseDetails.technologies.isEmpty(), tvTechnologyTitle, tvTechnologyContent
                )
            ) {
                val textTechnologies =
                    setupText(currentBinding.tvTechnologyContent, caseDetails.technologies)
                tvTechnologyContent.text = textTechnologies
            }
            if (!checkAndSetupGone(
                    caseDetails.platforms.isEmpty(), tvPlatformsTitle, tvPlatformsContent
                )
            ) {
                val textPlatforms =
                    setupText(currentBinding.tvPlatformsContent, caseDetails.platforms)
                tvPlatformsContent.text = textPlatforms
            }
        }
    }

    private fun setupBlockNextCase() {
        val currentBinding = BlockNextCaseBinding.bind(binding.root)
        with(currentBinding) {
            val case = caseBox.caseNext
            if (case != null) {
                loadImage(ivNextCase, case.image)
            } else {
                tvNextCaseTitle.visibility = View.GONE
                cvNextCase.visibility = View.GONE
            }
        }
    }

    private fun setupBlockButton(caseDetails: CaseDetails) {
        val currentBinding = BlockButtonCaseBinding.bind(binding.root)
        with(currentBinding) {
            checkAndSetupGone(caseDetails.iOSUrl.isEmpty(), btAppStore)
            checkAndSetupGone(caseDetails.androidUrl.isEmpty(), btGooglePlay)
            checkAndSetupGone(caseDetails.webUrl.isEmpty(), btWebsite)
        }
        currentBinding.btAppStore.setOnClickListener {
            this@CaseFragment.openInternet(caseDetails.iOSUrl)
        }
        currentBinding.btGooglePlay.setOnClickListener {
            this@CaseFragment.openInternet(caseDetails.androidUrl)
        }
        currentBinding.btWebsite.setOnClickListener {
            this@CaseFragment.openInternet(caseDetails.webUrl)
        }
    }

    private fun loadImage(
        imageView: ImageView,
        url: String,
        cornerRadius: Int? = null,
    ) {
        if (cornerRadius != null) {
            Glide.with(requireContext()).load(url).roundCorners(cornerRadius).into(imageView)
        } else {
            Glide.with(requireContext()).load(url).into(imageView)
        }
    }

    private fun onClockEmail() {
        binding.btSendRequestCase.btSendApp.setOnClickListener {
            sendEmail(URL_EMAIL)
        }
    }

    private fun onClickHomeTitle() {
        binding.tvGoToHome.setOnClickListener {
            router.backTo(Screens.openHomeFragment())
        }
    }

    private fun onClickImages() {
        binding.icBlockImages.llBlockImages.setOnClickListener {
            router.navigateTo(Screens.openImagesFragment(imageState.value))
        }
    }

    private fun onClickSendApp() {
        binding.btSendRequestCase.btSendApp.setOnClickListener {
            router.navigateTo(Screens.openApplicationFragment())
        }
    }

    companion object {
        private const val CASE_BOX = "case_box"
        private const val URL_EMAIL = "hello@it-cron.ru"
        fun newInstance(caseBox: CaseBox) = CaseFragment().apply {
            arguments = bundleOf(CASE_BOX to caseBox)
        }
    }
}