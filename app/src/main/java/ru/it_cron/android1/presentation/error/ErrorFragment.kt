package ru.it_cron.android1.presentation.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.android1.databinding.FragmentErrorBinding


class ErrorFragment : Fragment() {

    private var _binding: FragmentErrorBinding? = null
    private val binding: FragmentErrorBinding
        get() = _binding ?: throw IllegalStateException("ErrorPageBinding is null")

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivClose.ivClose.setOnClickListener {
            router.exit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ErrorFragment()
    }
}