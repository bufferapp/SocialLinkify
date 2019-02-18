package org.buffer.sociallinkify.span

import android.content.ActivityNotFoundException
import android.net.Uri
import android.text.style.URLSpan
import android.util.Log
import android.view.View
import org.buffer.sociallinkify.util.CustomTabUtil

abstract class ClickableUrlSpan(url: String) : URLSpan(url) {

    override fun onClick(widget: View) {
        try {
            if (url.contains(":")) {
                CustomTabUtil.open(widget.context, Uri.parse(url))
            } else {
                CustomTabUtil.open(widget.context, Uri.parse("http://$url"))
            }
        } catch (exception: ActivityNotFoundException) {
            Log.e(ClickableUrlSpan::class.java.simpleName,
                "Whoops, that doesn't look like a valid URL.")
        }
    }
}
