package ru.it_cron.android1.presentation.cases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.it_cron.android1.databinding.FragmentCasesBinding

class CasesFragment : Fragment() {
    private var _binding: FragmentCasesBinding? = null
    private val binding: FragmentCasesBinding
        get() = _binding?: throw IllegalStateException("FragmentCasesBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCasesBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CasesFragment()
    }
}