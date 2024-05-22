package ru.it_cron.android1.presentation.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.android1.databinding.FragmentPersonalInfoBinding

class PersonalInfoFragment : Fragment() {

    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding: FragmentPersonalInfoBinding
        get() = _binding ?: throw IllegalStateException("FragmentPersonalInfoBinding is null")

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonalInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btPersonalInfoOk.btSendApp.setOnClickListener {
            setFragmentResult(
                KEY_FRAGMENT_PI,
                bundleOf(FLAG_SELECTION_PI to true)
            )
            router.exit()
        }
    }

    companion object {
        const val KEY_FRAGMENT_PI = "personal_info"
        const val FLAG_SELECTION_PI = "personal_info_selection"

        @JvmStatic
        fun newInstance() = PersonalInfoFragment()
    }
}

