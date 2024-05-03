package ru.it_cron.android1.presentation.home


import android.content.Context
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

    private lateinit var launchIntent: LaunchIntent

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LaunchIntent) {
            launchIntent = context
        } else {
            throw IllegalStateException("Activity must implement LaunchIntent")
        }
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
                launchIntent.launchEmail()
            }
            ivFacebook.setOnClickListener {
                launchIntent.launchFacebook()
            }
            ivInstagram.setOnClickListener {
                launchIntent.launchInstagram()
            }
            ivTelegram.setOnClickListener {
                launchIntent.launchTelegram()
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

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    interface LaunchIntent {
        fun launchFacebook()
        fun launchInstagram()
        fun launchTelegram()
        fun launchEmail()
    }
}