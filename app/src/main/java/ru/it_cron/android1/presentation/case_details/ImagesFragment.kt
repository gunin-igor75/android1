package ru.it_cron.android1.presentation.case_details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.core.os.bundleOf
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.RequestManager
import com.github.terrakok.cicerone.Router
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import ru.it_cron.android1.R
import ru.it_cron.android1.databinding.FragmentImagesBinding
import ru.it_cron.android1.domain.model.filter.ContainerImage

class ImagesFragment : Fragment() {

    private var _binding: FragmentImagesBinding? = null
    private val binding: FragmentImagesBinding
        get() = _binding ?: throw IllegalStateException(
            "FragmentImagesBinding is null"
        )

    private val containerData: ContainerImage by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(IMAGE, ContainerImage::class.java) as ContainerImage
        } else {
            arguments?.getParcelable(IMAGE) ?: throw IllegalStateException(
                "Image is null"
            )
        }
    }
    private val router: Router by inject()

    private val glide by inject<RequestManager>()

    private var currentJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clSplash.setBackgroundColor(containerData.colorId)
        val indicators = getIdNewIndicators(containerData)
        binding.vpSplash.adapter = ImagesViewPagerAdapter(containerData, glide)
        settingViewPager(indicators)
        onClickButtonClose()
    }


    private fun getIdNewIndicators(containerData: ContainerImage): List<LinearProgressIndicator> {
        val indicators = mutableListOf<LinearProgressIndicator>()
        binding.llIndicators.setBackgroundColor(containerData.colorId)
        val size = containerData.images.size
        for (i in 0 until size) {
            val width = resources.getDimension(R.dimen.width_progress).toInt()
            val height = resources.getDimension(R.dimen.height_progress).toInt()
            val margin = resources.getDimension(R.dimen.margin_progress).toInt()
            val layoutParams = LayoutParams(width, height)
            val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.images_progress_indicator, null) as LinearProgressIndicator
            layoutParams.setMargins(margin)
            view.layoutParams = layoutParams
            binding.llIndicators.addView(view)
            indicators.add(view)
        }
        return indicators
    }

    private fun settingViewPager(indicators: List<LinearProgressIndicator>) {

        binding.vpSplash.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicators.forEachIndexed { index, indicator ->
                    if (index != position) {
                        indicator.progress = 0
                        currentJob?.cancel()
                    }
                }
                currentJob?.cancel()
                currentJob = lifecycleScope.async {
                    for (value in BEGIN_INTERVAL..END_INTERVAL) {
                        delay(DELAY_TIME)
                        indicators[position].progress = value
                    }
                    if (position < indicators.size - 1) {
                        binding.vpSplash.currentItem += 1
                    } else {
                        router.exit()
                    }
                }
            }
        })
    }

    private fun onClickButtonClose() {
        binding.btClose.ivClose.setOnClickListener {
            currentJob?.cancel()
            router.exit()
        }
    }

    companion object {
        private const val IMAGE = "image"
        private const val BEGIN_INTERVAL = 0
        private const val END_INTERVAL = 100
        private const val DELAY_TIME = 30L

        @JvmStatic
        fun newInstance(containerData: ContainerImage) =
            ImagesFragment().apply {
                arguments = bundleOf(IMAGE to containerData)
            }
    }
}