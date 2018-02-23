package org.buffer.sociallinkify

import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.style.URLSpan
import android.util.Patterns
import org.buffer.sociallinkify.model.PatternType
import org.buffer.sociallinkify.model.SocialNetwork
import org.buffer.sociallinkify.span.CustomTabUrlSpan
import org.buffer.sociallinkify.span.HashtagMentionUrlSpan
import java.util.regex.Pattern

object SocialLinkify {

    val URL_BASE_MENTION_TWITTER = "https://twitter.com/"
    val URL_BASE_HASHTAG_TWITTER = "https://twitter.com/search?q="

    val URL_BASE_MENTION_INSTAGRAM = "https://www.instagram.com/"
    val URL_BASE_HASHTAG_INSTAGRAM = "https://www.instagram.com/explore/tags/"

    val URL_BASE_MENTION_FACEBOOK = "https://www.facebook.com/"
    val URL_BASE_HASHTAG_FACEBOOK = "https://www.facebook.com/hashtag/"

    private val hashtagPattern = Pattern.compile("#(\\w+)")
    private val mentionPattern = Pattern.compile("@(\\w+)")
    private val urlPattern = Patterns.WEB_URL

    private val patterns = mapOf<PatternType, Pattern>(PatternType.HASHTAG to hashtagPattern,
            PatternType.MENTION to mentionPattern, PatternType.URL to urlPattern)

    fun socialLinkifyText(color: Int, text: String?, socialNetwork: SocialNetwork,
                          vararg patternTypes: PatternType): Spanned {
        text?.let {
            val spannable = SpannableString(text)
            patternTypes
                    .map { patterns[it]?.matcher(spannable) }
                    .forEachIndexed { index, matcher ->
                        matcher?.let {
                            while (matcher.find()) {
                                val start = matcher.start()
                                val end = matcher.end()
                                val spanText = text.substring(start, end)
                                val url = buildSpan(color, spanText, socialNetwork,
                                        patternTypes[index])
                                spannable.setSpan(url, start, end, 0)
                            }
                        }
                    }
            return spannable
        }
        return SpannedString("")
    }

    private fun buildSpan(color: Int, text: String, socialNetwork: SocialNetwork,
                          patternType: PatternType): URLSpan {
        return if (patternType == PatternType.URL) {
            CustomTabUrlSpan(color, text)
        } else {
            HashtagMentionUrlSpan(color, buildUrl(text, socialNetwork, patternType))
        }
    }

    private fun buildUrl(entity: String, socialNetwork: SocialNetwork, patternType: PatternType):
            String {
        return if (patternType == PatternType.HASHTAG) {
            getHashtagUrlBase(socialNetwork) + entity.substring(1, entity.length)
        } else {
            getMentionUrlBase(socialNetwork) + entity.substring(1, entity.length)
        }
    }

    private fun getMentionUrlBase(socialNetwork: SocialNetwork): String {
        return when (socialNetwork) {
            SocialNetwork.FACEBOOK -> {
                URL_BASE_MENTION_FACEBOOK
            }
            SocialNetwork.INSTAGRAM -> {
                URL_BASE_MENTION_INSTAGRAM
            }
            else -> {
                URL_BASE_MENTION_TWITTER
            }
        }
    }

    private fun getHashtagUrlBase(socialNetwork: SocialNetwork): String {
        return when (socialNetwork) {
            SocialNetwork.FACEBOOK -> {
                URL_BASE_HASHTAG_FACEBOOK
            }
            SocialNetwork.INSTAGRAM -> {
                URL_BASE_HASHTAG_INSTAGRAM
            }
            else -> {
                URL_BASE_HASHTAG_TWITTER
            }
        }
    }
}
