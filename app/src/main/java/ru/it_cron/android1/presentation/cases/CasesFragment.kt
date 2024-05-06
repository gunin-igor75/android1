package ru.it_cron.android1.presentation.cases

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.android1.databinding.FragmentCasesBinding
import ru.it_cron.android1.domain.model.StateApp
import ru.it_cron.android1.navigation.Screens
import ru.it_cron.android1.presentation.cases.CasesAdapter.CaseOnClickListener

class CasesFragment : Fragment() {
    private var _binding: FragmentCasesBinding? = null
    private val binding: FragmentCasesBinding
        get() = _binding ?: throw IllegalStateException("FragmentCasesBinding is null")

    private val viewModel: CasesViewModel by viewModel()

    private val casesAdapter: CasesAdapter by inject()

    private val router: Router by inject()


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
        onClickAdapter()
    }

    private fun setupToolBarApp() {
        val toolBar = binding.tbCases
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolBar)
        toolBar.setNavigationOnClickListener {
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
            viewModel.cases
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
                    when (state) {
                        is StateApp.Initial -> {}
                        is StateApp.Loading -> {
                            binding.pbCases.visibility = View.VISIBLE
                        }

                        is StateApp.Success -> {
                            binding.pbCases.visibility = View.GONE
                            casesAdapter.submitList(state.value)
                        }

                        is StateApp.ErrorInternet -> {
                            router.replaceScreen(Screens.openErrorFragment())
                        }

                        is StateApp.Error -> {
                            Log.e(TAG, state.error)
                        }
                    }
                }
        }
    }

    private fun onClickAdapter() {
        casesAdapter.caseOnClickListener = object : CaseOnClickListener {
            override fun onClickCase(caseId: String) {
                router.navigateTo(Screens.openCaseFragment(caseId))
            }
        }
    }

    companion object {
        private const val TAG = "CasesFragment"

        @JvmStatic
        fun newInstance() = CasesFragment()
    }
}