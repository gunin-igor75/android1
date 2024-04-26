package ru.it_cron.android1.presentation.error

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.it_cron.android1.databinding.ErrorPageBinding


class ErrorFragment : Fragment() {

    private var _binding: ErrorPageBinding? = null
    private val binding: ErrorPageBinding
        get() = _binding ?: throw IllegalStateException("ErrorPageBinding is null")

    private lateinit var onFinishedListener: OnFinishedListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFinishedListener) {
            onFinishedListener = context
        } else {
            throw IllegalStateException("Activity must implement OnFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ErrorPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {
            onFinishedListener.onFinishErrorFragment()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            onFinishedListener.onFinishErrorFragment()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ErrorFragment()
    }

    interface OnFinishedListener {
        fun onFinishErrorFragment()
    }
}