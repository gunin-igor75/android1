package ru.it_cron.android1.presentation.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.FragmentSendAppOkBinding
import ru.it_cron.android1.presentation.extension.callPhone

class SendAppOkFragment : Fragment() {

    private var _binding: FragmentSendAppOkBinding? = null
    private val binding: FragmentSendAppOkBinding
        get() = _binding ?: throw IllegalStateException("FragmentSendAppOkBinding is null")

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSendAppOkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickButtonOk()
        onClickPhoneCall()
    }

    private fun onClickPhoneCall() {
        binding.llPhoneNumber.setOnClickListener {
            callPhone(resources.getString(R.string.phone_number))
        }
    }

    private fun onClickButtonOk() {
        binding.inButtonOk.btSendApp.setOnClickListener {
            router.exit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SendAppOkFragment()
    }
}