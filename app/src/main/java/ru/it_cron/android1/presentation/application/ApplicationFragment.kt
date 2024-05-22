package ru.it_cron.android1.presentation.application

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.FragmentApplicationBinding
import ru.it_cron.android1.navigation.Screens
import ru.it_cron.android1.presentation.application.PersonalInfoFragment.Companion.FLAG_SELECTION_PI
import ru.it_cron.android1.presentation.application.PersonalInfoFragment.Companion.KEY_FRAGMENT_PI
import ru.it_cron.android1.presentation.application.PoliticFragment.Companion.FLAG_SELECTION_PP
import ru.it_cron.android1.presentation.application.PoliticFragment.Companion.KEY_FRAGMENT_PP
import ru.it_cron.android1.presentation.application.adapters.ApplicationAdapter
import ru.it_cron.android1.presentation.extension.callPhone
import ru.it_cron.android1.presentation.utils.makeLinks

class ApplicationFragment : Fragment() {

    private var _binding: FragmentApplicationBinding? = null
    private val binding: FragmentApplicationBinding
        get() = _binding ?: throw IllegalStateException("FragmentApplicationBinding is null")

    private val router: Router by inject()

    private val viewModel by viewModel<ApplicationViewModel>()

    private val serviceAdapter by lazy {
        ApplicationAdapter()
    }
    private val budgetAdapter by lazy {
        ApplicationAdapter()
    }
    private val areaActivityAdapter by lazy {
        ApplicationAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickBack()
        createSpannableWordPhone()
        createSpannableWordPersonalInfo()
        createSpannableWordPolitic()
        setupRecyclerView()
        onClickAdaptersItem()

    }

    private fun setupRecyclerView() {
        binding.rvServices.adapter = serviceAdapter
        binding.rvServices.suppressLayout(false)
        binding.rvBudgets.adapter = budgetAdapter
        binding.rvBudgets.suppressLayout(false)
        binding.rvAreaActivity.adapter = areaActivityAdapter
        binding.rvAreaActivity.suppressLayout(false)
        viewModel.services.observe(viewLifecycleOwner) {
            serviceAdapter.submitList(it)
        }
        viewModel.budgets.observe(viewLifecycleOwner) {
            budgetAdapter.submitList(it)
        }
        viewModel.areaActivity.observe(viewLifecycleOwner) {
            areaActivityAdapter.submitList(it)
        }
    }

    private fun onClickAdaptersItem() {
        serviceAdapter.serviceItemOnClickListener =
            object : ApplicationAdapter.ServiceItemOnClickListener {
                override fun onClickItem(name: String) {
                    viewModel.toggleService(name)
                }
            }
        budgetAdapter.serviceItemOnClickListener =
            object : ApplicationAdapter.ServiceItemOnClickListener {
                override fun onClickItem(name: String) {
                    viewModel.toggleBudget(name)
                }
            }

        areaActivityAdapter.serviceItemOnClickListener =
            object : ApplicationAdapter.ServiceItemOnClickListener {
                override fun onClickItem(name: String) {
                    viewModel.toggleAreaActivity(name)
                }
            }
    }

    private fun createSpannableWordPhone() {
        val view = binding.inBriefApp.tvAppPhone
        val listener = View.OnClickListener {
            callPhone(resources.getString(R.string.phone_number))
        }
        val color = ContextCompat.getColor(
            binding.root.context, R.color.orange
        )
        val spannableString = makeLinks(
            text = view.text.toString(),
            phrase = resources.getString(R.string.phrase_phone),
            phraseColor = color,
            listener = listener
        )

        view.movementMethod = LinkMovementMethod.getInstance()
        view.setText(
            spannableString, TextView.BufferType.SPANNABLE
        )
    }

    private fun createSpannableWordPersonalInfo() {
        val view = binding.chPersonalInfo
        val listener = View.OnClickListener {
            setFragmentResultListener(KEY_FRAGMENT_PI) { _, bundle ->
                binding.chPersonalInfo.isChecked = bundle.getBoolean(FLAG_SELECTION_PI)
            }
            router.navigateTo(Screens.openPersonalInfoFragment())
        }
        val color = ContextCompat.getColor(
            binding.root.context, R.color.orange
        )
        val spannableString = makeLinks(
            text = view.text.toString(),
            phrase = resources.getString(R.string.checkbox_app_span_1),
            phraseColor = color,
            listener = listener
        )

        view.movementMethod = LinkMovementMethod.getInstance()
        view.setText(
            spannableString, TextView.BufferType.SPANNABLE
        )
    }

    private fun createSpannableWordPolitic() {
        val view = binding.chPolitic
        val listener = View.OnClickListener {
            setFragmentResultListener(KEY_FRAGMENT_PP) { _, bundle ->
                binding.chPolitic.isChecked = bundle.getBoolean(FLAG_SELECTION_PP)
            }
            router.navigateTo(Screens.openPoliticFragment())
        }
        val color = ContextCompat.getColor(
            binding.root.context, R.color.orange
        )
        val spannableString = makeLinks(
            text = view.text.toString(),
            phrase = resources.getString(R.string.checkbox_app_span_2),
            phraseColor = color,
            listener = listener
        )

        view.movementMethod = LinkMovementMethod.getInstance()
        view.setText(
            spannableString, TextView.BufferType.SPANNABLE
        )
    }

    private fun onClickBack() {
        val toolBar = binding.tbApp
        toolBar.setNavigationOnClickListener {
            viewModel.clearChoces()
            router.exit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ApplicationFragment()
    }
}
