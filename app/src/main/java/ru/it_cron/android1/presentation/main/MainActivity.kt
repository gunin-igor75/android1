package ru.it_cron.android1.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.android1.R
import ru.it_cron.android1.domain.model.AvailableState
import ru.it_cron.android1.presentation.error.ErrorFragment
import ru.it_cron.android1.presentation.home.HomeFragment
import ru.it_cron.android1.presentation.onboarding.AppIntro

class MainActivity : FragmentActivity(), ErrorFragment.OnFinishedListener,
    HomeFragment.LaunchIntent {

    private val navHolder: NavigatorHolder by inject()
    private val viewModel: MainViewModel by viewModel<MainViewModel>()
    private val navigator: AppNavigator by lazy {
        AppNavigator(this, R.id.main_container, supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Android1)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                changeIsLoading()
            }
        }
        viewModel.checkAvailable()
        launchStartFragment()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navHolder.removeNavigator()
        super.onPause()
    }

    override fun onFinishErrorFragment() {
        setupFragment()
    }

    override fun launchFacebook() {
        sendRequest(URL_FACEBOOK, PN_FACEBOOK)
    }

    override fun launchInstagram() {
        sendRequest(URL_INSTAGRAM, PN_INSTAGRAM)
    }

    override fun launchTelegram() {
        sendRequest(URL_TELEGRAM, PN_TELEGRAM)
    }

    override fun launchEmail() {
        sendEmail(URL_EMAIL)
    }

    private fun launchStartFragment() {
        lifecycleScope.launch {
            viewModel.isAvailable
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is AvailableState.Success -> {
                            setupFragment()
                        }

                        is AvailableState.Error -> {
                            launchFragment(ErrorFragment.newInstance())
                        }

                        AvailableState.Initial -> {
                        }
                    }
                }
        }
    }

    private fun setupFragment() {
        viewModel.isCompleted.observe(this) { completed ->
            val fragment = if (completed) HomeFragment.newInstance() else AppIntro.newInstance()
            launchFragment(fragment)
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    private fun changeIsLoading(): Boolean {
        var isLoading = false
        viewModel.isLoading.observe(this) {
            isLoading = it
        }
        return isLoading
    }

    private fun sendRequest(url: String, packageName: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                intent.setPackage(packageName)
            }
            startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
                }
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
            Log.d(TAG, addresses)
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
        private const val TAG = "MainActivity"
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