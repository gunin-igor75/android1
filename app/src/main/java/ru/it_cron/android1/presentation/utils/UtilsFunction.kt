package ru.it_cron.android1.presentation.utils

import android.widget.TextView

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


