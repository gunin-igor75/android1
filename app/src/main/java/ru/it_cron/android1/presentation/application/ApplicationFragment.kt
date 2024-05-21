package ru.it_cron.android1.presentation.application

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.FragmentApplicationBinding
import ru.it_cron.android1.presentation.extension.callPhone
import ru.it_cron.android1.presentation.utils.makeLinks

class ApplicationFragment : Fragment() {

    private var _binding: FragmentApplicationBinding? = null
    private val binding: FragmentApplicationBinding
        get() = _binding ?: throw IllegalStateException("FragmentApplicationBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createSpannableWord()
    }

    private fun createSpannableWord() {
        val view = binding.inBriefApp.tvAppPhone
        val listener = View.OnClickListener {
            callPhone(PHONE_NUMBER)
        }
        val color = ContextCompat.getColor(
            binding.root.context,
            R.color.orange
        )
        val spannableString = makeLinks(
            text = view.text.toString(),
            phrase = PHRASE,
            phraseColor = color,
            listener = listener
        )

        view.movementMethod = LinkMovementMethod.getInstance()
        view.setText(
            spannableString,
            TextView.BufferType.SPANNABLE
        )
    }

    companion object {
        private const val PHRASE = "+7 (495) 006–13–57"
        private const val PHONE_NUMBER = "74950861357"

        @JvmStatic
        fun newInstance() = ApplicationFragment()
    }
}
