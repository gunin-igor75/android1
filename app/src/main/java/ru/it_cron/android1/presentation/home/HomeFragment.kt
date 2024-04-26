package ru.it_cron.android1.presentation.home


import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.FragmentHomeBinding
import ru.it_cron.android1.navigation.Screens
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
        with(binding) {
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
                changeColorButton(btSendRequest)
                /*TODO Send Request realisation*/
            }
        }
    }

    private fun changeColorButton(button: Button) {
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.button_trans)
        val transition = drawable as TransitionDrawable
        button.background = transition
        transition.startTransition(TIME_DELAY)
        button.isEnabled = false
    }

    private fun launchBodyContent() {
        with(binding.inBodyContent) {
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
        private const val TIME_DELAY = 300
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
