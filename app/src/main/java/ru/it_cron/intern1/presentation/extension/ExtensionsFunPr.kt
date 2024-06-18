package ru.it_cron.intern1.presentation.extension

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.it_cron.intern1.constant.BMP
import ru.it_cron.intern1.constant.DOC
import ru.it_cron.intern1.constant.JAR
import ru.it_cron.intern1.constant.JPEG
import ru.it_cron.intern1.constant.PDF
import ru.it_cron.intern1.constant.PNG
import ru.it_cron.intern1.constant.RAR
import ru.it_cron.intern1.constant.SVG
import ru.it_cron.intern1.constant.TXT
import ru.it_cron.intern1.constant.XLS
import ru.it_cron.intern1.constant.XLSX
import ru.it_cron.intern1.constant.ZIP
import ru.it_cron.intern1.presentation.dialog.DialogFragmentEmail


private const val PN_GMAIL = "com.google.android.gm"
private const val PN_PLAY_MARKET = "com.android.vending"
private const val URL_PLAY_MARKET = "market://launch"
private const val URL_GMAIL = "mailto:"
private const val KEY_ID = "id"
private const val KEY_LAUNCH = "launch"
private const val VALUE_TRUE = "true"
private const val TAG = "NetworkLoader"
private const val APP = "app"
private const val TEXT_TYPE_SHARE = "text/plain"
private val exrList = listOf(
    TXT, PNG, SVG, JPEG, BMP, PDF,
    DOC, XLS, XLSX, ZIP, RAR, JAR
)

fun Fragment.sendRequest(
    url: String,
    packageName: String,
) {
    var intent = requireActivity().packageManager.getLaunchIntentForPackage(packageName)
    if (intent != null) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } else {
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }
}

fun Fragment.sendEmail(
    addresses: String,
) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(URL_GMAIL)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(addresses))
        }
        startActivity(intent)
    } catch (e: Exception) {
        DialogFragmentEmail {
            val intent = Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=$addresses"));
            startActivity(intent);
        }.show(requireActivity().supportFragmentManager, "custom")
    }
}

fun Fragment.openInternet(
    url: String,
) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
        }
        startActivity(intent)
    } catch (e: Exception) {
        Log.d(TAG, e.message.toString())
    }
}

fun Fragment.callPhone(
    phoneNumber: String,
) {
    val call = Uri.parse("tel:$phoneNumber")
    val intent = Intent(Intent.ACTION_DIAL, call)
    startActivity(intent)
}

fun Fragment.shareIn(
    text: String,
) {
    val clazz = this::class.java
    val tag = clazz.simpleName
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = TEXT_TYPE_SHARE
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    try {
        startActivity(shareIntent)
    } catch (e: ActivityNotFoundException) {
        Log.d(tag, e.message.toString())
    }
}

fun <T> RequestBuilder<T>.roundCorners(cornerRadius: Int) =
    apply(RequestOptions().transform(RoundedCorners(cornerRadius)))


fun Fragment.dpToPx(dpValue: Int): Int {
    val dpRatio = resources.displayMetrics.density
    return (dpValue * dpRatio).toInt()
}

fun String.getExtension(): String {
    val ext = substringAfter('.').lowercase()
    return if (exrList.contains(ext)) {
        ext
    } else {
        APP
    }
}

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.getParcelableProvider(identifierParameter: String): T? {

    return if (Build.VERSION.SDK_INT >= 33) {
        this.getParcelable(identifierParameter, T::class.java)
    } else {
        this.getParcelable(identifierParameter)
    }

}

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.getParcelableArrayListProvider(identifierParameter: String): java.util.ArrayList<T>? {

    return if (Build.VERSION.SDK_INT >= 33) {
        this.getParcelableArrayList(identifierParameter, T::class.java)
    } else {
        this.getParcelableArrayList(identifierParameter)
    }
}
