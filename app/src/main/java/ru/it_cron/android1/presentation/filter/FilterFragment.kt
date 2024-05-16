package ru.it_cron.android1.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.it_cron.android1.databinding.FragmentFilterBinding
import ru.it_cron.android1.domain.model.StateError
import ru.it_cron.android1.domain.model.StateScreen
import ru.it_cron.android1.navigation.Screens
import ru.it_cron.android1.presentation.filter.FilterAdapter.FilterItemOnClickListener

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding
        get() = _binding ?: throw IllegalStateException("FragmentFilterBinding is null")

    private val viewModel: FiltersViewModel by inject()

    private val router: Router by inject()

    private val filterAdapter: FilterAdapter by lazy {
        FilterAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        observeViewModelError()
        observeIconClear()
        setupToolBarApp()
        setupOnClick()
        onClickIconClear()
    }

    private fun onClickIconClear() {
        binding.ivCleaner.setOnClickListener {
            viewModel.clearFilter()
        }
    }

    private fun observeIconClear() {
        viewModel.isEnabled.observe(viewLifecycleOwner) { state ->
            if (state) {
                binding.ivCleaner.visibility = View.VISIBLE
            } else {
                binding.ivCleaner.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerViewFilters = binding.rvFilters
        recyclerViewFilters.animation = null
        recyclerViewFilters.adapter = filterAdapter
    }

    private fun setupOnClick() {
        filterAdapter.filterItemOnClickListener = object : FilterItemOnClickListener {
            override fun onClickFilterItem(filterId: String) {
                viewModel.toggle(filterId)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
                    when (state) {
                        is StateScreen.Initial -> {}
                        is StateScreen.Loading -> {
                            binding.pbFilters.visibility = View.VISIBLE
                        }

                        is StateScreen.Success -> {
                            binding.pbFilters.visibility = View.GONE
                            setupUiComponent()
                        }
                    }
                }
        }
    }

    private fun setupUiComponent() {
        viewModel.typeItems.observe(viewLifecycleOwner) {
            filterAdapter.submitList(it)
        }
    }

    private fun observeViewModelError() {
        lifecycleScope.launch {
            viewModel.error
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
                    when (state) {
                        is StateError.Error -> {
                            Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG)
                                .show()
                        }

                        StateError.ErrorInternet -> {
                            router.replaceScreen(Screens.openErrorFragment())
                        }
                    }
                }
        }
    }

    private fun setupToolBarApp() {
        val toolBar = binding.tbCases
        toolBar.setNavigationOnClickListener {
            router.exit()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = FilterFragment()
    }
}