package org.buffer.sociallinkify.model

enum class SocialNetwork(val id: String) {
    TWITTER("twitter"),
    FACEBOOK("facebook"),
    INSTAGRAM("instagram"),
    LINKEDIN("linkedin"),
    PINTEREST("pinterest"),
    GOOGLE_PLUS("google");

    companion object {
        fun fromString(name: String) = when (name) {
            TWITTER.id -> TWITTER
            FACEBOOK.id -> FACEBOOK
            INSTAGRAM.id -> INSTAGRAM
            LINKEDIN.id -> LINKEDIN
            PINTEREST.id -> PINTEREST
            GOOGLE_PLUS.id -> GOOGLE_PLUS
            else -> throw IllegalArgumentException("Whoops, the social network $name isn't known!")
        }
    }
}
