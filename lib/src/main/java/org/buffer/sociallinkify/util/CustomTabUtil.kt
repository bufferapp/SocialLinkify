package org.buffer.sociallinkify.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.util.TypedValue
import org.buffer.sociallinkify.R

object CustomTabUtil {

    fun open(context: Context, uri: Uri) {
        context.startActivity(buildIntent(context, uri))
    }

    private fun buildIntent(context: Context, uri: Uri): Intent {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        val color = typedValue.data
        val customTabsIntent = CustomTabsIntent.Builder()
                .setToolbarColor(color)
                .setShowTitle(true)
                .addDefaultShareMenuItem()
                .build()
        val intent = customTabsIntent.intent
        intent.data = uri
        return intent
    }
}
}