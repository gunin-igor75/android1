package ru.it_cron.android1.presentation.company

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.android1.R
import ru.it_cron.android1.constant.PN_FACEBOOK
import ru.it_cron.android1.constant.PN_INSTAGRAM
import ru.it_cron.android1.constant.PN_TELEGRAM
import ru.it_cron.android1.constant.URL_EMAIL
import ru.it_cron.android1.constant.URL_FACEBOOK
import ru.it_cron.android1.constant.URL_INSTAGRAM
import ru.it_cron.android1.constant.URL_TELEGRAM
import ru.it_cron.android1.databinding.BlockCommunicationsCompanyBinding
import ru.it_cron.android1.databinding.FragmentCompanyBinding
import ru.it_cron.android1.presentation.extension.sendEmail
import ru.it_cron.android1.presentation.extension.sendRequest
import ru.it_cron.android1.presentation.utils.makeLinks

class CompanyFragment : Fragment() {
    private var _binding: FragmentCompanyBinding? = null
    private val binding: FragmentCompanyBinding
        get() = _binding ?: throw IllegalStateException("FragmentCompanyBinding is null")

    private val router: Router by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        onClickMenuListener()
        onClickBack()
        joinTeam()
        onClickViewListener()
    }

    private fun onClickViewListener() {
        val blockCommunicationsBinding = BlockCommunicationsCompanyBinding.bind(binding.root)
        with(blockCommunicationsBinding) {
            tvCommunicationsInstagram.setOnClickListener {
                sendRequest(URL_INSTAGRAM, PN_INSTAGRAM)
            }
            tvCommunicationsFacebook.setOnClickListener {
                sendRequest(URL_FACEBOOK, PN_FACEBOOK)
            }
            tvCommunicationsTelegram.setOnClickListener {
                sendRequest(URL_TELEGRAM, PN_TELEGRAM)
            }
        }
        binding.inCommunicationsAddress.ivTelegram.setOnClickListener {
            sendRequest(URL_TELEGRAM, PN_TELEGRAM)
        }
        binding.inBlockClientCompany.tvContactsInfoEmail.setOnClickListener {
            sendEmail(URL_EMAIL)
        }
    }

    private fun joinTeam() {
        val view = binding.inJoinTeam.tvJoinTeamSlogan
        val listener = View.OnClickListener {
            sendEmail(URL_EMAIL)
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


    private fun setupMenu() {
        binding.tbCompany.inflateMenu(R.menu.fragment_company)
    }

    private fun onClickMenuListener() {
        binding.tbCompany.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.mItCron -> {
                    true
                }

                R.id.mInstagram -> {
                    sendRequest(URL_INSTAGRAM, PN_INSTAGRAM)
                    true
                }

                R.id.mFacebook -> {
                    sendRequest(URL_FACEBOOK, PN_FACEBOOK)
                    true
                }

                R.id.mTelegram -> {
                    sendRequest(URL_TELEGRAM, PN_TELEGRAM)
                    true
                }

                else -> false
            }
        }
    }

    private fun onClickBack() {
        val toolBar = binding.tbCompany
        toolBar.setNavigationOnClickListener {
            router.exit()
        }
    }

    companion object {

        private const val PHRASE = "hr@it-cron.ru"

        @JvmStatic
        fun newInstance() = CompanyFragment()
    }
}