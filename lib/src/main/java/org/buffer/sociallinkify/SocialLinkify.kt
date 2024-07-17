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

    val URL_BASE_MENTION_BLUESKY = "https://bsky.app/profile/"
    val URL_BASE_HASHTAG_BLUESKY = "https://bsky.app/search?q="

    val URL_BASE_MENTION_INSTAGRAM = "https://www.instagram.com/"
    val URL_BASE_HASHTAG_INSTAGRAM = "https://www.instagram.com/explore/tags/"

    val URL_BASE_MENTION_FACEBOOK = "https://www.facebook.com/"
    val URL_BASE_HASHTAG_FACEBOOK = "https://www.facebook.com/hashtag/"

    val URL_YOUTUBE_HASHTAG = "https://www.youtube.com/results?search_query=%23"
    private val hashtagPattern = Pattern.compile("#(\\w+)")
    private val mentionPattern = Pattern.compile("@([a-zA-Z0-9._]+)")
    private val urlPattern = Patterns.WEB_URL
    private val emailPattern = Patterns.EMAIL_ADDRESS

    private val patterns = mapOf<PatternType, Pattern>(PatternType.EMAIL to emailPattern,
            PatternType.URL to urlPattern, PatternType.HASHTAG to hashtagPattern,
            PatternType.MENTION to mentionPattern)

    fun socialLinkifyText(
        color: Int, text: String?, socialNetwork: SocialNetwork,
        vararg patternTypes: PatternType, server: String? = null
    ): Spanned {
        text ?: return SpannedString("")

        val spannable = SpannableString(text)

        patternTypes
                .mapNotNull { patterns[it]?.matcher(spannable) }
                .forEachIndexed { index, matcher ->
                    with(matcher) {
                        while (this.find()) {
                            val start = this.start()
                            val end = this.end()
                            val spanText = text.substring(start, end)
                            val spans = spannable.getSpans(start, end, URLSpan::class.java)
                            if (spans.isEmpty()) {
                                val url = buildSpan(color, spanText, socialNetwork,
                                        patternTypes[index], server)
                                spannable.setSpan(url, start, end, 0)
                            }
                        }
                    }
                }

        return spannable
    }

    private fun buildSpan(color: Int, text: String, socialNetwork: SocialNetwork,
                          patternType: PatternType, server: String?  = null) = when (patternType) {
        PatternType.URL -> CustomTabUrlSpan(color, text)
        PatternType.EMAIL -> HashtagMentionUrlSpan(color,
                buildUrl(text, socialNetwork, patternType))
        else -> HashtagMentionUrlSpan(color, buildUrl(text, socialNetwork, patternType, server))
    }

    private fun buildUrl(
        entity: String, socialNetwork: SocialNetwork,
        patternType: PatternType, server: String? = null
    ) = when (patternType) {
        PatternType.HASHTAG -> getHashtagUrlBase(socialNetwork, server) + entity.substring(1, entity.length)
        PatternType.EMAIL -> "mailto:$entity"
        else -> getMentionUrlBase(socialNetwork, server) + entity.substring(1, entity.length)
    }

    private fun getMentionUrlBase(socialNetwork: SocialNetwork, server: String? = null) = when (socialNetwork) {
        SocialNetwork.FACEBOOK -> URL_BASE_MENTION_FACEBOOK
        SocialNetwork.INSTAGRAM -> URL_BASE_MENTION_INSTAGRAM
        SocialNetwork.MASTODON -> "https://$server/@"
        SocialNetwork.BLUESKY -> URL_BASE_MENTION_BLUESKY
        else -> URL_BASE_MENTION_TWITTER
    }

    private fun getHashtagUrlBase(socialNetwork: SocialNetwork, server: String? = null) = when (socialNetwork) {
        SocialNetwork.FACEBOOK -> URL_BASE_HASHTAG_FACEBOOK
        SocialNetwork.INSTAGRAM -> URL_BASE_HASHTAG_INSTAGRAM
        SocialNetwork.YOUTUBE -> URL_YOUTUBE_HASHTAG
        SocialNetwork.MASTODON -> "https://$server/tags/"
        SocialNetwork.BLUESKY -> URL_BASE_HASHTAG_BLUESKY
        else -> URL_BASE_HASHTAG_TWITTER
    }
}
