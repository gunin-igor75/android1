package ru.it_cron.android1.presentation.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.android1.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding: FragmentContactsBinding
        get() = _binding ?: throw IllegalStateException("FragmentContactsBinding is null")

    private val viewModel: ContactsViewModel by viewModel()

    private val daysItemAdapter by lazy {
        DaysItemAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvScheduleWork.adapter = daysItemAdapter
    }

    private fun observeViewModel() {
        viewModel.daysItems.observe(viewLifecycleOwner) {
            daysItemAdapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }
}