package ru.it_cron.android1.presentation.contacts

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
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.android1.R
import ru.it_cron.android1.constant.PN_FACEBOOK
import ru.it_cron.android1.constant.PN_INSTAGRAM
import ru.it_cron.android1.constant.PN_TELEGRAM
import ru.it_cron.android1.constant.URL_EMAIL
import ru.it_cron.android1.constant.URL_FACEBOOK
import ru.it_cron.android1.constant.URL_INSTAGRAM
import ru.it_cron.android1.constant.URL_TELEGRAM
import ru.it_cron.android1.databinding.FragmentContactsBinding
import ru.it_cron.android1.navigation.Screens
import ru.it_cron.android1.presentation.extension.sendEmail
import ru.it_cron.android1.presentation.extension.sendRequest
import ru.it_cron.android1.presentation.extension.shareIn
import ru.it_cron.android1.presentation.utils.makeLinks

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding: FragmentContactsBinding
        get() = _binding ?: throw IllegalStateException("FragmentContactsBinding is null")

    private val viewModel: ContactsViewModel by viewModel()

    private val daysItemAdapter by lazy {
        DaysItemAdapter(requireContext())
    }

    private val router: Router by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        onClickMenuListener()
        setupRecyclerView()
        observeViewModel()
        omClickBack()
        onClickBlockLinks()
        onClickWordJoinTeam()
        onClickBlockClientCompany()
        onClickBlockCommunicationAddress()
        sendApplication()

    }

    private fun setupMenu() {
        binding.tbContacts.inflateMenu(R.menu.fragment_contacts)
    }

    private fun onClickMenuListener() {
        binding.tbContacts.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.mShare -> {
                    onClickShareIcon()
                    true
                }

                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvScheduleWork.adapter = daysItemAdapter
    }

    private fun observeViewModel() {
        viewModel.daysItems.observe(viewLifecycleOwner) {
            daysItemAdapter.submitList(it)
        }
    }

    private fun omClickBack() {
        binding.tbContacts.setNavigationOnClickListener {
            router.exit()
        }
    }

    private fun onClickBlockLinks() {
        with(binding.inLinks) {
            tvInstagram.setOnClickListener {
                sendRequest(URL_INSTAGRAM, PN_INSTAGRAM)
            }
            tvFacebook.setOnClickListener {
                sendRequest(URL_FACEBOOK, PN_FACEBOOK)
            }
            tvTelegram.setOnClickListener {
                sendRequest(URL_TELEGRAM, PN_TELEGRAM)
            }
        }
    }

    private fun onClickWordJoinTeam() {
        val view = binding.inJoinTeamContacts.tvJoinTeamSlogan
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

    private fun onClickBlockClientCompany() {
        binding.inBlockClientCompany.tvContactsInfoEmail.setOnClickListener {
            sendEmail(URL_EMAIL)
        }
    }

    private fun onClickBlockCommunicationAddress() {
        binding.inCommunicationsAddress.ivTelegram.setOnClickListener {
            sendRequest(URL_TELEGRAM, PN_TELEGRAM)
        }
    }

    private fun sendApplication() {
        binding.btSendApp.btSendApp.setOnClickListener {
            router.navigateTo(Screens.openApplicationFragment())
        }
    }

    private fun onClickShareIcon() {
        val arrayText = resources.getStringArray(R.array.requisites_array)
        val text = arrayText.joinToString("\n")
        shareIn(text)
    }

    companion object {
        private const val PHRASE = "hr@it-cron.ru"

        @JvmStatic
        fun newInstance() = ContactsFragment()
    }
}