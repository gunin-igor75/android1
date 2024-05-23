package ru.it_cron.android1.presentation.application

import android.graphics.PorterDuff
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
import ru.it_cron.android1.presentation.application.watchers.TextWatcherPhone
import ru.it_cron.android1.presentation.application.watchers.TextWatcherSimple
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
        setupEditTextTask(
            binding.inTask.tilTask,
            binding.inTask.tetTask
        )
        setupEditTextNameAndCompany(
            binding.inContactsInfo.tilName,
            binding.inContactsInfo.tetName
        )
        setupEditTextNameAndCompany(
            binding.inContactsInfo.tilCompany,
            binding.inContactsInfo.tetCompany
        )
        setupEditTextEmail(
            binding.inContactsInfo.tilEmail,
            binding.inContactsInfo.tetEmail
        )
        setupEditTextPhone(
            binding.inContactsInfo.tilPhone,
            binding.inContactsInfo.tetPhone
        )

    }

    private fun setupEditTextTask(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
    ) {
        textInputEditText.setOnFocusChangeListener { _, _ ->
            textInputLayout.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.orange)
        }
        val textWatcher = TextWatcherSimple { text ->
            val isValid = validationText(text)
            viewModel.sendResultInput(isValid)
        }
        textInputEditText.addTextChangedListener(textWatcher)
    }

    private fun setupEditTextNameAndCompany(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
    ) {
        textInputLayout.setEndIconTintMode(PorterDuff.Mode.DST)
        textInputEditText.setOnFocusChangeListener { _, _ ->
            textInputLayout.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.orange)
        }
        val textWatcher = TextWatcherSimple { text ->
            val isValid = validationText(text)
            viewModel.sendResultInput(isValid)
            if (isValid) {
                textInputLayout.endIconDrawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_selected_16
                )
            }
        }
        textInputEditText.addTextChangedListener(textWatcher)
    }

    private fun setupEditTextEmail(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
    ) {
        textInputLayout.setEndIconTintMode(PorterDuff.Mode.DST)
        textInputEditText.setOnFocusChangeListener { _, _ ->
            textInputLayout.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.orange)
        }
        val textWatcher = TextWatcherSimple { text ->
            val isValid = validationEmailAndPhone(text, PATTERN_EMAIL)
            viewModel.sendResultInput(isValid)
            if (isValid) {
                textInputLayout.error = null
                textInputLayout.endIconDrawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_selected_16
                )
            } else {
                textInputLayout.error = getString(R.string.valid_email)
            }
        }
        textInputLayout.setErrorIconOnClickListener {
            textInputEditText.setText(getString(R.string.Empty))
            textInputLayout.error = null
        }
        textInputEditText.addTextChangedListener(textWatcher)
    }

    private fun setupEditTextPhone(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
    ) {
        textInputLayout.setEndIconTintMode(PorterDuff.Mode.DST)
        textInputEditText.setOnFocusChangeListener { _, _ ->
            textInputLayout.boxStrokeColor =
                ContextCompat.getColor(requireContext(), R.color.orange)
        }
        val textWatcher = TextWatcherPhone(
            editText = textInputEditText
        ) { text ->
            val isValid = validationEmailAndPhone(text, PATTERN_PHONE)
            viewModel.sendResultInput(isValid)
            if (isValid) {
                textInputLayout.error = null
                textInputLayout.endIconDrawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_selected_16
                )
            } else {
                textInputLayout.error = getString(R.string.valid_number_phone)
            }
        }
        textInputLayout.setErrorIconOnClickListener {
            textInputEditText.setText(getString(R.string.Empty))
            textInputLayout.error = null
        }
        textInputEditText.addTextChangedListener(textWatcher)
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
            viewModel.clearChoices()
            router.exit()
        }
    }

    private fun validationText(text: String): Boolean {
        return text.isNotBlank()
    }

    private fun validationEmailAndPhone(email: String, pattern: String): Boolean {
        val regex = pattern.toRegex()
        return regex.matches(email)
    }

    companion object {
        private const val PATTERN_EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"
        private const val PATTERN_PHONE = "^\\+7\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}\$"

        @JvmStatic
        fun newInstance() = ApplicationFragment()
    }
}
