package ru.it_cron.android1.presentation.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.android1.constant.PN_FACEBOOK
import ru.it_cron.android1.constant.PN_INSTAGRAM
import ru.it_cron.android1.constant.PN_TELEGRAM
import ru.it_cron.android1.constant.URL_EMAIL
import ru.it_cron.android1.constant.URL_FACEBOOK
import ru.it_cron.android1.constant.URL_INSTAGRAM
import ru.it_cron.android1.constant.URL_TELEGRAM
import ru.it_cron.android1.databinding.BodyHomeContentBinding
import ru.it_cron.android1.databinding.FooterHomeContentBinding
import ru.it_cron.android1.databinding.FragmentHomeBinding
import ru.it_cron.android1.navigation.Screens
import ru.it_cron.android1.presentation.animation.CustomAnimated
import ru.it_cron.android1.presentation.animation.CustomAnimated.Companion.animatedColor
import ru.it_cron.android1.presentation.extension.sendEmail
import ru.it_cron.android1.presentation.extension.sendRequest


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw IllegalStateException("FragmentHomeBinding is null")

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchBodyContent()
        launchFooterContent()
    }

    private fun launchFooterContent() {
        val footerBinding = FooterHomeContentBinding.bind(binding.root)
        with(footerBinding) {
            tvEmail.setOnClickListener {
                sendEmail(URL_EMAIL)
            }
            ivFacebook.setOnClickListener {
                sendRequest(URL_FACEBOOK, PN_FACEBOOK)
            }
            ivInstagram.setOnClickListener {
                sendRequest(URL_INSTAGRAM, PN_INSTAGRAM)
            }
            ivTelegram.setOnClickListener {
                sendRequest(URL_TELEGRAM, PN_TELEGRAM)
            }
            btSendRequest.setOnClickListener {
                CustomAnimated.animatedAlpha(
                    btSendRequest,
                ) {
                    /*TODO Send Request realisation*/
                }
            }
        }
    }

    private fun launchBodyContent() {
        val bodyBinding = BodyHomeContentBinding.bind(binding.root)
        with(bodyBinding) {
            tvCases.setOnClickListener {
                animatedColor(tvCases) {
                    router.navigateTo(Screens.openCasesFragment())
                }
            }
            tvCompany.setOnClickListener {
                animatedColor(tvCompany) {
                    router.navigateTo(Screens.openCompanyFragment())
                }
            }
            tvServices.setOnClickListener {
                animatedColor(tvServices) {
                    router.navigateTo(Screens.openServicesFragment())
                }
            }
            tvContacts.setOnClickListener {
                animatedColor(tvContacts) {
                    router.navigateTo(Screens.openContactsFragment())
                }
            }
            tvLogin.setOnClickListener {
                animatedColor(tvLogin) {
                    router.navigateTo(Screens.openLoginFragment())
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}

