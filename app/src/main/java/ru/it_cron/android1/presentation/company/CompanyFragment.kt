package ru.it_cron.android1.presentation.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.it_cron.android1.databinding.FragmentCompanyBinding

class CompanyFragment : Fragment() {
    private var _binding: FragmentCompanyBinding? = null
    private val binding: FragmentCompanyBinding
        get() = _binding?: throw IllegalStateException("FragmentCompanyBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CompanyFragment()
    }
}