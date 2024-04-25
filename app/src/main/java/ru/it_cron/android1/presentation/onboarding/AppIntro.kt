package ru.it_cron.android1.presentation.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.IntroAppContentBinding
import ru.it_cron.android1.navigation.Screens

class AppIntro : Fragment() {

    private var _binding: IntroAppContentBinding? = null
    private val binding: IntroAppContentBinding
        get() = _binding ?: throw IllegalStateException("IntroAppContentBinding is null")
    private val viewModel: OnBoardingViewModel by viewModel<OnBoardingViewModel>()

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = IntroAppContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpIntro.adapter = AppIntroViewPagerAdapter()
        binding.vpIntro.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupView(position)
            }
        })
        binding.btIntro.setOnClickListener{
            val current = binding.vpIntro.currentItem
            if (current == STEP - 1) {
                viewModel.saveOnBoardingState(true)
                Log.d("MainActivity", "button click")
                router.replaceScreen(Screens.openHomeFragment())
            } else {
                binding.vpIntro.currentItem = current + 1
            }
        }
    }

    private fun setupView(position: Int) {
        if (position == STEP - 1) {
            binding.btIntro.text = getString(R.string.bt_begin)
            binding.btIntro.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.circle_bg_orange
            )
        } else {
            binding.btIntro.text = getString(R.string.bt_next)
            binding.btIntro.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.circle_bg_black
            )
        }
    }

    companion object {
        const val STEP = 3
        fun newInstance() = AppIntro()
    }
}