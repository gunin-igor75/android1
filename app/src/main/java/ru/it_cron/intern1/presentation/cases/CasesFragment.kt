package ru.it_cron.intern1.presentation.cases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.intern1.R
import ru.it_cron.intern1.databinding.FragmentCasesBinding
import ru.it_cron.intern1.domain.model.StateError
import ru.it_cron.intern1.domain.model.StateScreen
import ru.it_cron.intern1.domain.model.cases.CaseBox
import ru.it_cron.intern1.navigation.Screens
import ru.it_cron.intern1.presentation.cases.CasesAdapter.CaseOnClickListener

class CasesFragment : Fragment() {
    private var _binding: FragmentCasesBinding? = null
    private val binding: FragmentCasesBinding
        get() = _binding ?: throw IllegalStateException("FragmentCasesBinding is null")

    private val viewModel: CasesViewModel by viewModel()

    private val glide by inject<RequestManager>()

    private val router: Router by inject()

    private val casesAdapter: CasesAdapter by lazy {
        CasesAdapter(glide)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCasesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBarApp()
        setupRecyclerView()
        observeViewModel()
        observeViewModelError()
        onClickAdapter()
        onClickFilter()
        changeColorFilters()
        setUpOnBackPressed()
    }

    private fun changeColorFilters() {
        viewModel.isEnabled.observe(viewLifecycleOwner) { state ->
            binding.tvFilter.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (state) R.color.orange else R.color.white
                )
            )
        }
    }

    private fun onClickFilter() {
        binding.tvFilter.setOnClickListener {
            router.navigateTo(Screens.openFiltersFragment())
        }
    }

    private fun setupToolBarApp() {
        val toolBar = binding.tbCases
        toolBar.setNavigationOnClickListener {
            viewModel.clearFilter()
            router.exit()
        }
    }

    private fun setupRecyclerView() {
        val recyclerViewCases = binding.rvCases
        recyclerViewCases.animation = null
        recyclerViewCases.adapter = casesAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
                    when (state) {
                        is StateScreen.Initial -> {}
                        is StateScreen.Loading -> {
                            binding.pbCases.visibility = View.VISIBLE
                        }

                        is StateScreen.Success -> {
                            binding.pbCases.visibility = View.GONE
                            launchContent()
                        }
                    }
                }
        }
    }

    private fun launchContent() {
        viewModel.casesWithFilters.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.flCaseNotFound.visibility = View.VISIBLE
            } else {
                binding.flCaseNotFound.visibility = View.GONE
                casesAdapter.submitList(it)
            }
        }
    }

    private fun observeViewModelError() {
        lifecycleScope.launch {
            viewModel.error
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
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

    private fun onClickAdapter() {
        casesAdapter.caseOnClickListener = object : CaseOnClickListener {
            override fun onClickCase(caseBox: CaseBox) {
                router.navigateTo(Screens.openCaseFragment(caseBox))
            }
        }
    }

    private fun setUpOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    viewModel.clearFilter()
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = CasesFragment()
    }
}