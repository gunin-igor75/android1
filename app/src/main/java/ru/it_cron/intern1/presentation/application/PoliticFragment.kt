package ru.it_cron.intern1.presentation.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.intern1.databinding.FragmentPoliticBinding


class PoliticFragment : Fragment() {

    private var _binding: FragmentPoliticBinding? = null
    private val binding: FragmentPoliticBinding
        get() = _binding ?: throw IllegalStateException("FragmentPoliticBinding is nul")

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPoliticBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btPoliticOk.btSendApp.setOnClickListener {
            setFragmentResult(
                KEY_FRAGMENT_PP,
                bundleOf(FLAG_SELECTION_PP to true)
            )
            router.exit()
        }
    }

    companion object {
        const val KEY_FRAGMENT_PP = "politic"
        const val FLAG_SELECTION_PP = "politic_selection"

        @JvmStatic
        fun newInstance() = PoliticFragment()
    }
}