package ru.it_cron.android1.presentation.home


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.android1.databinding.BodyHomeContentBinding
import ru.it_cron.android1.databinding.FooterHomeContentBinding
import ru.it_cron.android1.databinding.FragmentHomeBinding
import ru.it_cron.android1.navigation.Screens
import ru.it_cron.android1.presentation.animation.CustomAnimated
import ru.it_cron.android1.presentation.animation.CustomAnimated.Companion.animatedColor


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
                ){
                    router.navigateTo(Screens.openContactsFragment())
                }
                /*TODO Send Request realisation*/
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

    private fun sendRequest(url: String, packageName: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setPackage(packageName)
            startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
            }
            startActivity(intent)
        }
    }
    private fun sendEmail(addresses: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse(URL_GMAIL)
                putExtra(Intent.EXTRA_EMAIL, addresses)
            }
            startActivity(intent)
        } catch (e: Exception) {
            val uriBuilder = Uri.parse(URL_PLAY_MARKET)
                .buildUpon()
                .appendQueryParameter(KEY_ID, PN_GMAIL)
                .appendQueryParameter(KEY_LAUNCH, VALUE_TRUE)

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = uriBuilder.build()
                setPackage(PN_PLAY_MARKET)
            }
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()

        private const val PN_TELEGRAM = "org.telegram.messenger"
        private const val PN_FACEBOOK = "com.facebook.katana"
        private const val PN_INSTAGRAM = "com.instagram.android"
        private const val PN_GMAIL = "com.google.android.gm"
        private const val URL_TELEGRAM = "https://t.me/+NnhpGqJYWAU2MDIy"
        private const val URL_FACEBOOK = "https://www.facebook.com/it.cron.ru/"
        private const val URL_INSTAGRAM = "https://www.instagram.com/itcron/?hl=ru"
        private const val URL_EMAIL = "hello@it-cron.ru"
        private const val PN_PLAY_MARKET = "com.android.vending"
        private const val URL_PLAY_MARKET = "market://launch"
        private const val URL_GMAIL = "mailto:"
        private const val KEY_ID = "id"
        private const val KEY_LAUNCH = "launch"
        private const val VALUE_TRUE = "true"
    }
}

