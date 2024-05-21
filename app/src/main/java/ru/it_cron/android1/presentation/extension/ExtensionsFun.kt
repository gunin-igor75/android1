package ru.it_cron.android1.presentation.extension

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


private const val PN_GMAIL = "com.google.android.gm"
private const val PN_PLAY_MARKET = "com.android.vending"
private const val URL_PLAY_MARKET = "market://launch"
private const val URL_GMAIL = "mailto:"
private const val KEY_ID = "id"
private const val KEY_LAUNCH = "launch"
private const val VALUE_TRUE = "true"
private const val TAG = "NetworkLoader"

fun Fragment.sendRequest(
    url: String,
    packageName: String,
) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage(packageName)
        startActivity(intent)
    } catch (e: Exception) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
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
            putExtra(Intent.EXTRA_EMAIL, addresses)
        }
        startActivity(intent)
    } catch (e: Exception) {
        val uriBuilder = Uri.parse(URL_PLAY_MARKET)
            .buildUpon()
            .appendQueryParameter(KEY_ID, PN_GMAIL)
            .appendQueryParameter(KEY_LAUNCH, VALUE_TRUE)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = uriBuilder.build()
            setPackage(PN_PLAY_MARKET)
        }
        startActivity(intent)
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

fun <T> RequestBuilder<T>.roundCorners(cornerRadius: Int) =
    apply(RequestOptions().transform(RoundedCorners(cornerRadius)))


