package ru.it_cron.intern1.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.it_cron.intern1.R
import ru.it_cron.intern1.databinding.IntroAppDesignBinding
import ru.it_cron.intern1.presentation.onboarding.AppIntro.Companion.STEP
import ru.it_cron.intern1.presentation.onboarding.AppIntroViewPagerAdapter.PagerViewHolder

class AppIntroViewPagerAdapter : RecyclerView.Adapter<PagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = IntroAppDesignBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun getItemCount(): Int = STEP

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.itemView.run {
            with(holder) {
                when (position) {
                    0 -> {
                        binding.ivOnboarding.setImageResource(R.drawable.onboarding_first)
                        binding.tvFirst.text = context.getString(R.string.onboarding_first)
                        binding.tvSecond.text = context.getString(R.string.onboarding_first_descr)
                    }

                    1 -> {
                        binding.ivOnboarding.setImageResource(R.drawable.onboarding_second)
                        binding.tvFirst.text = context.getString(R.string.onboarding_second)
                        binding.tvSecond.text = context.getString(R.string.onboarding_second_descr)
                    }

                    2 -> {
                        binding.ivOnboarding.setImageResource(R.drawable.onboarding_third)
                        binding.tvFirst.text = context.getString(R.string.onboarding_third)
                        binding.tvSecond.text = context.getString(R.string.onboarding_third_descr)
                    }

                    else -> IllegalStateException(
                        "Position $position >= size pager: $STEP"
                    )
                }
            }
        }
    }

    class PagerViewHolder(val binding: IntroAppDesignBinding) :
        RecyclerView.ViewHolder(binding.root)
}