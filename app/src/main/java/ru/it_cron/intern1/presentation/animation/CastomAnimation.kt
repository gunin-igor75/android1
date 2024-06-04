package ru.it_cron.intern1.presentation.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.animation.LinearInterpolator
import android.widget.TextView


class CustomAnimated {

    companion object {
        private const val FIELD = "textColor"
        private const val COLOR_BEGIN = "#FFFFFFFF"
        private const val COLOR_END = "#FF5A5959"
        private const val TIME_DELAY = 300L
        fun animatedColor(
            textView: TextView,
            action: () -> Unit,
        ) {
            val animator = ObjectAnimator.ofArgb(
                textView,
                FIELD,
                Color.parseColor(COLOR_BEGIN),
                Color.parseColor(COLOR_END)
            ).apply {
                duration = TIME_DELAY
                interpolator = LinearInterpolator()
            }
            val listener = object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    action()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }
            animator.addListener(listener)
            animator.start()
        }

        fun animatedAlpha(
            textView: TextView,
            action: () -> Unit,
        ) {
            val animator = ObjectAnimator.ofFloat(
                textView,
                "alpha",
                1f,
                0.3f
            ).apply {
                duration = TIME_DELAY
                interpolator = LinearInterpolator()
            }
            val listener = object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    action()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }
            animator.addListener(listener)
            animator.start()
        }
    }
}


