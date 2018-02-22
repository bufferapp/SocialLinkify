package org.buffer.sociallinkify.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.customtabs.CustomTabsIntent
import android.util.TypedValue

object CustomTabUtil {

    fun open(context: Context, uri: Uri) {
        context.startActivity(buildIntent(context, uri))
    }

    private fun buildIntent(context: Context, uri: Uri): Intent {
        val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .addDefaultShareMenuItem()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
            val color = typedValue.data
            customTabsIntent.setToolbarColor(color)
        }

        val intent = customTabsIntent.build().intent
        intent.data = uri
        return intent
    }
}