package ru.it_cron.intern1.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.intern1.R
import ru.it_cron.intern1.domain.model.AvailableState
import ru.it_cron.intern1.navigation.Screens

class MainActivity : AppCompatActivity() {

    private val navHolder: NavigatorHolder by inject()
    private val viewModel: MainViewModel by viewModel<MainViewModel>()
    private val navigator: AppNavigator by lazy {
        AppNavigator(this, R.id.mainContainer, supportFragmentManager)
    }

    private val router: Router by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Android1)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.checkAvailable()
        checkDataServer()
        setupScreen()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navHolder.removeNavigator()
        super.onPause()
    }

    private fun checkDataServer() {
        lifecycleScope.launch {
            viewModel.isAvailable
                .flowWithLifecycle(lifecycle)
                .collect { state ->
                    when (state) {
                        is AvailableState.Success -> {
                        }

                        is AvailableState.Error -> {
                            launchToast(state.error)
                        }

                        AvailableState.Initial -> {
                        }
                    }
                }
        }
    }

    private fun launchToast(text: String) {
        Toast.makeText(
            this,
            "Error: $text",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setupScreen() {
        viewModel.isCompleted.observe(this) { completed ->
            lifecycleScope.launch {
                delay(DELAY_SPLASH)
                if (!completed) {
                    router.replaceScreen(Screens.openOnBoardingFragment())
                } else {
                    router.replaceScreen(Screens.openHomeFragment())
                }
            }
        }
    }

    companion object{
        private const val DELAY_SPLASH = 1000L
    }
}
