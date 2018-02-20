package org.buffer.sociallinkify.span

import android.net.Uri
import android.text.style.URLSpan
import android.view.View
import org.buffer.sociallinkify.util.CustomTabUtil

abstract class ClickableUrlSpan constructor(url: String): URLSpan(url) {

    override fun onClick(widget: View) {
        CustomTabUtil.open(widget.context, Uri.parse(url))
    }
}