package ru.it_cron.android1.presentation.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

fun setupText(textView: TextView, list: List<String>): String {
    val result = StringBuilder()
    var currentLine = ""
    for (world in list) {
        if (!isTooLarge(textView, "$currentLine $world")) {
            result.append(" ").append(world)
            currentLine += " $world"
        } else {
            result.append("\n").append(world)
            currentLine = world
        }
    }
    return result.trim().toString()
}

private fun isTooLarge(textView: TextView, text: String): Boolean {
    val textWidth = textView.paint.measureText(text)
    return textWidth >= textView.measuredWidth
}

fun makeLinks(
    text: String,
    phrase: String,
    phraseColor: Int,
    listener: View.OnClickListener,
): SpannableString {
    val spannableString = SpannableString(text)
    val clickableSpan = object : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            ds.isFakeBoldText = true
            ds.color = phraseColor
        }

        override fun onClick(widget: View) {
            widget.cancelPendingInputEvents()
            listener.onClick(widget)
        }
    }
    val start = text.indexOf(phrase)
    val end = start + phrase.length
    spannableString.setSpan(
        clickableSpan,
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}

fun Fragment.dpToPx(dpValue: Int): Int {
    val dpRatio = resources.displayMetrics.density
    return (dpValue * dpRatio).toInt()
}
