package ru.it_cron.android1.presentation.case_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.FragmentCaseBinding
import ru.it_cron.android1.presentation.extension.arguments

class CaseFragment : Fragment() {

    private var _binding: FragmentCaseBinding? = null

    private val binding: FragmentCaseBinding
        get() = _binding ?: throw IllegalStateException("FragmentCaseBinding is null")

    private val caseId: String by lazy { arguments?.getString(CASE_ID).orEmpty() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_case, container, false)
    }

    companion object {

        private const val CASE_ID = "case_id"

        fun newInstance(caseId: String) = CaseFragment()
            .arguments(CASE_ID to caseId)
    }
}