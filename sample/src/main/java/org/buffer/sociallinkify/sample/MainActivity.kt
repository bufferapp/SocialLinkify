package org.buffer.sociallinkify.sample

import android.app.Activity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.buffer.sociallinkify.SocialLinkify
import org.buffer.sociallinkify.model.PatternType
import org.buffer.sociallinkify.model.PatternType.*
import org.buffer.sociallinkify.model.SocialNetwork

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTextViews()
    }

    private fun initTextViews() {
        tvTwitterMention.mentionify(R.string.twitter_mention, SocialNetwork.TWITTER)
        tvTwitterHashtag.hashtagify(R.string.twitter_hashtag, SocialNetwork.TWITTER)
        tvInstagramMention.mentionify(R.string.instagram_mention, SocialNetwork.INSTAGRAM)
        tvInstagramHashtag.hashtagify(R.string.instagram_hashtag, SocialNetwork.INSTAGRAM)
        tvFacebookHashtag.hashtagify(R.string.facebook_hashtag, SocialNetwork.FACEBOOK)
    }

    fun TextView.hashtagify(stringResId: Int,
                            socialNetwork: SocialNetwork) {
        linkify(stringResId, socialNetwork, HASHTAG)
    }

    fun TextView.mentionify(stringResId: Int,
                            socialNetwork: SocialNetwork) {
        linkify(stringResId, socialNetwork, MENTION)
    }

    fun TextView.linkify(stringResId: Int,
                         socialNetwork: SocialNetwork,
                         patternType: PatternType = URL) {
        movementMethod = LinkMovementMethod.getInstance()
        text = SocialLinkify.socialLinkifyText(
                resources.getColor(R.color.colorAccent),
                getString(stringResId),
                socialNetwork,
                patternType
        )
    }
}
