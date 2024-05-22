package ru.it_cron.android1.presentation.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.it_cron.android1.databinding.FragmentPoliticBinding


class PoliticFragment : Fragment() {

    private var _binding: FragmentPoliticBinding? = null
    private val binding: FragmentPoliticBinding
        get() = _binding ?: throw IllegalStateException("FragmentPoliticBinding is nul")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPoliticBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = PoliticFragment()
    }
}