package org.buffer.sociallinkify.span

import android.text.TextPaint

class HashtagMentionUrlSpan constructor(private val color: Int, url: String): ClickableUrlSpan(url) {

    override fun updateDrawState(ds: TextPaint) {
        ds.color = color
        ds.isUnderlineText = false
    }

}