package ru.it_cron.android1.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.android1.R
import ru.it_cron.android1.domain.model.AvailableState
import ru.it_cron.android1.navigation.Screens

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
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainContainer)) { v, insets ->
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
            if (!completed) {
                router.replaceScreen(Screens.openOnBoardingFragment())
            }
        }
    }

    private fun changeIsLoading(): Boolean {
        var isLoading = false
        viewModel.isLoading.observe(this) {
            isLoading = it
        }
        return isLoading
    }
}
