package ru.it_cron.intern1.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.it_cron.intern1.databinding.FragmentDialogEmalBinding

class DialogFragmentEmail(
    private val action: () -> Unit,
) : DialogFragment() {
    private var _binding: FragmentDialogEmalBinding? = null
    private val binding: FragmentDialogEmalBinding
        get() = _binding ?: throw IllegalStateException()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDialogEmalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickGooglePlay()
        onClickCancel()
    }

    private fun onClickCancel() {
        binding.tvCancel.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun onClickGooglePlay() {
        binding.tvGooglePlay.setOnClickListener {
            dialog?.cancel()
            action()
        }
    }
}