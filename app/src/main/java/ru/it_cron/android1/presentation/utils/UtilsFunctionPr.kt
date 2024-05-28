package ru.it_cron.android1.presentation.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import ru.it_cron.android1.R
import ru.it_cron.android1.constant.BMP
import ru.it_cron.android1.constant.DOC
import ru.it_cron.android1.constant.JAR
import ru.it_cron.android1.constant.JPEG
import ru.it_cron.android1.constant.PDF
import ru.it_cron.android1.constant.PNG
import ru.it_cron.android1.constant.RAR
import ru.it_cron.android1.constant.SVG
import ru.it_cron.android1.constant.TXT
import ru.it_cron.android1.constant.XLS
import ru.it_cron.android1.constant.XLSX
import ru.it_cron.android1.constant.ZIP
import ru.it_cron.android1.presentation.extension.getExtension


private const val TEXT_TYPE = "text/plain"
private const val IMAGE_TYPE = "image/*"
private const val APPLICATION_TYPE = "application/*"


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

fun getMimeType(fileName: String): String {
    val ext = fileName.getExtension()
    return when (ext) {
        TXT -> {
            TEXT_TYPE
        }

        JPEG, PNG, SVG, BMP -> {
            IMAGE_TYPE
        }

        else -> {
            APPLICATION_TYPE
        }
    }
}

fun getColorIdFile(fileName: String): Int {
    val ext = fileName.getExtension()
    return when (ext) {
        JPEG, PNG, SVG, BMP -> R.color.file_image
        PDF -> R.color.file_pdf
        DOC -> R.color.file_doc
        ZIP, JAR, RAR -> R.color.file_archive
        XLS, XLSX -> R.color.file_xls
        TXT -> R.color.file_txt
        else -> R.color.file_other
    }
}


